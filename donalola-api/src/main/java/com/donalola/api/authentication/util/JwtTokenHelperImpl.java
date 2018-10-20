package com.donalola.api.authentication.util;

import com.donalola.api.config.jwt.JwtConfig;
import lombok.extern.slf4j.Slf4j;
import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jwk.HttpsJwks;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.keys.resolvers.HttpsJwksVerificationKeyResolver;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class JwtTokenHelperImpl implements JwtTokenHelper {

    private JwtConsumer jwtConsumer;

    private final String jwksEndpoint;
    private final String tokenIssuer;

    public JwtTokenHelperImpl(JwtConfig jwtConfig) {
        this.jwksEndpoint = String.format("https://cognito-idp.%s.amazonaws.com/%s/.well-known/jwks.json", jwtConfig.getAwsRegion(), jwtConfig.getCognitoUserPoolId());
        this.tokenIssuer = jwtConfig.getTokenIssuer();
    }

    @Override
    public JwtConsumer getJwtConsumer() {
        if (!Optional.ofNullable(this.jwtConsumer).isPresent()) {
            if (log.isDebugEnabled()) {
                log.debug("Getting JWKS from: " + this.jwksEndpoint);
            }
            this.jwtConsumer = new JwtConsumerBuilder()
                    .setVerificationKeyResolver(new HttpsJwksVerificationKeyResolver(new HttpsJwks(this.jwksEndpoint)))
                    .setJweAlgorithmConstraints(AlgorithmConstraints.DISALLOW_NONE)
                    .setRequireExpirationTime()
                    .setExpectedAudience(false, "7c2m1mt33pqss49jb9013j0jp3")
                    .setExpectedIssuer(this.tokenIssuer)
                    .build();
        }
        return this.jwtConsumer;
    }
}
