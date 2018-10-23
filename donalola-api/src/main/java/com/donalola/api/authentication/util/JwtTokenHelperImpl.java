package com.donalola.api.authentication.util;

import com.donalola.api.authentication.jwt.AWSCognitoToken;
import com.donalola.api.authentication.jwt.FirebaseToken;
import com.donalola.api.authentication.jwt.JwtToken;
import com.donalola.api.config.jwt.JwtConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jwk.HttpsJwks;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.keys.resolvers.HttpsJwksVerificationKeyResolver;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class JwtTokenHelperImpl implements JwtTokenHelper {
    @Override
    public JwtConsumer getJwtConsumer(final JwtToken jwtToken) {
        if (AWSCognitoToken.class.isAssignableFrom(jwtToken.getClass())) {
            return this.getJwtCognitoConsumer();
        }
        if (FirebaseToken.class.isAssignableFrom(jwtToken.getClass())) {
            return this.getJwtFirebaseConsumer(jwtToken);
        }
        throw new UnsupportedOperationException("Unsupported Jwt Type");
    }

    private JwtConsumer jwtCognitoConsumer;
    private JwtConsumer jwtFirebaseConsumer;

    private final String jwksCognitoEndpoint;
    private final String tokenIssuer;
    private final String firebaseKeysEndPoint;
    private LocalDateTime refreshKeysOn;

    public JwtTokenHelperImpl(JwtConfig jwtConfig) {
        this.jwksCognitoEndpoint = String.format("https://cognito-idp.%s.amazonaws.com/%s/.well-known/jwks.json", jwtConfig.getAwsRegion(), jwtConfig.getCognitoUserPoolId());
        this.tokenIssuer = jwtConfig.getTokenIssuer();
        this.firebaseKeysEndPoint = jwtConfig.getFirebaseKeysEndPoint();
    }

    private JwtConsumer getJwtFirebaseConsumer(JwtToken jwtToken) {
        if (!Optional.ofNullable(this.jwtFirebaseConsumer).isPresent() || needRefreshFirebaseKeys()) {
            if (log.isDebugEnabled()) {
                log.debug("Getting public keys from: " + this.firebaseKeysEndPoint);
            }
            String keyAsString;
            try {
                keyAsString = getPublicKey(jwtToken.getKeyId());
            } catch (IOException e) {
                throw new BadCredentialsException("No public key found");
            }
            // decode the key into proper format
            InputStream certIs = new ByteArrayInputStream(Charset.forName("UTF-8").encode(keyAsString).array());

            CertificateFactory certificateFactory;
            try {
                certificateFactory = CertificateFactory.getInstance("X.509");
            } catch (CertificateException e) {
                throw new BadCredentialsException(e.getMessage());
            }

            X509Certificate cert;
            try {
                cert = (X509Certificate) certificateFactory.generateCertificate(certIs);
            } catch (CertificateException e) {
                throw new BadCredentialsException(e.getMessage());
            }
            PublicKey key = cert.getPublicKey();

            // now that we have the public key, we can verify the JWT
            this.jwtFirebaseConsumer = new JwtConsumerBuilder()
                    .setRequireExpirationTime() // the JWT must have an expiration time
                    .setMaxFutureValidityInMinutes(300) // but the  expiration time can't be too crazy
                    .setAllowedClockSkewInSeconds(30) // allow some leeway in validating time based claims to account for clock skew
                    .setRequireSubject() // the JWT must have a subject claim
                    .setExpectedIssuer("https://securetoken.google.com/dona-lola-inc") // whom the JWT needs to have been issued by
                    .setExpectedAudience("dona-lola-inc") // to whom the JWT is intended for
                    .setVerificationKey(key) // verify the signature with the public key
                    .build();

        }
        return this.jwtFirebaseConsumer;
    }

    private boolean needRefreshFirebaseKeys() {
        if (!Optional.ofNullable(this.refreshKeysOn).isPresent()) {
            return true;
        }
        return LocalDateTime.now().isAfter(this.refreshKeysOn);
    }

    private JwtConsumer getJwtCognitoConsumer() {
        if (!Optional.ofNullable(this.jwtCognitoConsumer).isPresent()) {
            if (log.isDebugEnabled()) {
                log.debug("Getting JWKS from: " + this.jwksCognitoEndpoint);
            }
            this.jwtCognitoConsumer = new JwtConsumerBuilder()
                    .setVerificationKeyResolver(new HttpsJwksVerificationKeyResolver(new HttpsJwks(this.jwksCognitoEndpoint)))
                    .setJweAlgorithmConstraints(AlgorithmConstraints.DISALLOW_NONE)
                    .setRequireExpirationTime()
                    .setExpectedAudience(false, "7c2m1mt33pqss49jb9013j0jp3")
                    .setExpectedIssuer(this.tokenIssuer)
                    .build();
        }
        return this.jwtCognitoConsumer;
    }

    private String getPublicKey(String kid) throws IOException {
        URL url = new URL(this.firebaseKeysEndPoint);
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.connect();
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> myMap = mapper.readValue(new InputStreamReader((InputStream) request.getContent()), HashMap.class);
        String cacheControl = request.getHeaderField("Cache-Control");
        String[] values = cacheControl.split(",");

        Optional<String> maxAgeDirective = Arrays.asList(values).parallelStream().filter(s -> StringUtils.contains(s, "max-age=")).findFirst();

        if (maxAgeDirective.isPresent()) {
            String maxAgeValue = StringUtils.replace(maxAgeDirective.get(), "max-age=", StringUtils.EMPTY);
            if (StringUtils.isNotEmpty(maxAgeValue)) {
                this.refreshKeysOn = LocalDateTime.now().plus(new Long(maxAgeValue.trim()), ChronoUnit.SECONDS);
            }
        }

        return myMap.get(kid);
    }
}
