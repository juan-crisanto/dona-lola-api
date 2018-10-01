package com.donalola.api.config.jwt;

import java.util.List;

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

    List<String> getMustBePresentIn();
}
