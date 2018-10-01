package com.donalola.api.config.jwt;

import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class ApiJwtConfig implements JwtConfig {

    @Value("${jwt.tokenIssuer}")
    private String tokenIssuer;

    @Value("${jwt.tokenSigningKey}")
    private String tokenSigningKey;

    @Value("${jwt.tokenExpirationTime}")
    private int tokenExpirationTime;

    @Value("${jwt.refreshTokenExpTime}")
    private int refreshTokenExpTime;

    @Value("${jwt.temporalAccessTokenExpTime}")
    private int temporalAccessTokenExpTime;

    @Value("${jwt.header}")
    private String authHeader;

    @Value("${jwt.cookie}")
    private String authCookie;

    @Value("${jwt.useCookie}")
    private boolean useCookie;

    @Value("#{'${jwt.mustBePresentIn}'.split(',')}")
    private List<String> mustBePresentIn;

    private SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
}
