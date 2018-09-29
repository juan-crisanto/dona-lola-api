package com.donalola.api.config.jwt;

public interface JwtConfig {

    String getTokenIssuer();

    String getTokenSigningKey();

    int getTokenExpirationTime();

    int getRefreshTokenExpTime();

    int getTemporalAccessTokenExpTime();

    String getAuthHeader();

    String getAuthCookie();

    boolean isUseCookie();

    io.jsonwebtoken.SignatureAlgorithm getSignatureAlgorithm();
}
