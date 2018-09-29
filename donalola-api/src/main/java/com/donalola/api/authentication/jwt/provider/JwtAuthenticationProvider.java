package com.donalola.api.authentication.jwt.provider;

import com.donalola.api.authentication.jwt.JwtToken;
import com.donalola.api.authentication.jwt.token.JwtAuthenticationToken;
import com.donalola.api.config.jwt.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtConfig jwtConfig;

    public JwtAuthenticationProvider(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtToken rawAccessToken = (JwtToken) authentication.getCredentials();
        Jws<Claims> jwsClaims = rawAccessToken.parseClaims(jwtConfig.getTokenSigningKey());

        return null;
    }

    public boolean supports(Class<?> aClass) {
        return JwtAuthenticationToken.class.isAssignableFrom(aClass);
    }
}
