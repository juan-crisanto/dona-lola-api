package com.donalola.api.authentication.jwt;

import io.jsonwebtoken.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;

import java.util.Optional;

public class AccessJwtToken implements JwtToken {

    private final String token;
    private final String tokenId;

    public AccessJwtToken(String token) {
        this.token = token;
        this.tokenId = StringUtils.EMPTY;
    }

    public AccessJwtToken(String token, String tokenId) {
        this.token = token;
        this.tokenId = tokenId;
    }

    @Override
    public String getToken() {
        return this.token;
    }

    @Override
    public String getTokenId() {
        if (!Optional.ofNullable(getTokenId()).isPresent()) {
            throw new IllegalStateException("Empty Id");
        }
        return this.tokenId;
    }

    @Override
    public Jws<Claims> parseClaims(String signingKey) {
        try {
            return Jwts.parser().setSigningKey(signingKey).parseClaimsJws(getToken());
        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException | SignatureException ex) {
            throw new BadCredentialsException("Invalid JWT token", ex);
        } catch (ExpiredJwtException expiredEx) {
            throw new CredentialsExpiredException("JWT Token expired", expiredEx);
        }
    }
}
