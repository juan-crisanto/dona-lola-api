package com.donalola.api.authentication.jwt;

import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.springframework.security.authentication.BadCredentialsException;

public abstract class AbstractToken implements JwtToken {

    @Override
    public JwtClaims parseClaims(JwtConsumer jwtConsumer) {
        try {
            JwtClaims jwtClaims = jwtConsumer.process(this.getToken()).getJwtClaims();
            jwtClaims.getExpirationTime();
            return jwtClaims;
        } catch (InvalidJwtException e) {
            throw new BadCredentialsException("Invalid JWT token: Cause " + e.getMessage(), e);
        } catch (MalformedClaimException e) {
            throw new BadCredentialsException("Invalid JWT token: Cause " + e.getMessage(), e);
        }
    }
}
