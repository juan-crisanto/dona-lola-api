package com.donalola.api.config.jwt;

import java.util.List;

public interface JwtConfig {

    String getTokenIssuer();

    String getAuthHeader();

    String getAuthCookie();

    boolean isUseCookie();

    String getAwsRegion();

    String getCognitoUserPoolId();

    List<String> getMustBePresentIn();
}
