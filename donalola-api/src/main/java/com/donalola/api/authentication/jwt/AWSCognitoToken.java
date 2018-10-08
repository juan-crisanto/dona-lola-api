package com.donalola.api.authentication.jwt;

import com.auth0.jwt.JWT;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.Optional;

public class AWSCognitoToken implements JwtToken {

    private final String token;
    private final String keyId;

    public AWSCognitoToken(String token) {
        this.token = token;
        this.keyId = JWT.decode(this.token).getKeyId();
    }

    @Override
    public String getToken() {
        return this.token;
    }

    @Override
    public String getKeyId() {
        if (!Optional.ofNullable(this.keyId).isPresent()) {
            throw new IllegalStateException("Empty Key Id");
        }
        return this.keyId;
    }

    @Override
    public JwtClaims parseClaims(JwtConsumer jwtConsumer) {
        try {
            JwtClaims jwtClaims = jwtConsumer.process(this.token).getJwtClaims();
            jwtClaims.getExpirationTime();
            return jwtClaims;
        } catch (InvalidJwtException e) {
            throw new BadCredentialsException("Invalid JWT token: Cause " + e.getMessage(), e);
        } catch (MalformedClaimException e) {
            throw new BadCredentialsException("Invalid JWT token: Cause " + e.getMessage(), e);
        }
    }
}
