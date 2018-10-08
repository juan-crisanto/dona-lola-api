package com.donalola.api.config.jwt;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class ApiJwtConfig implements JwtConfig {

    @Value("${jwt.tokenIssuer}")
    private String tokenIssuer;

    @Value("${jwt.header}")
    private String authHeader;

    @Value("${jwt.cookie}")
    private String authCookie;

    @Value("${jwt.useCookie}")
    private boolean useCookie;

    @Value("#{'${jwt.mustBePresentIn}'.split(',')}")
    private List<String> mustBePresentIn;

    @Value("${jwt.aws.region}")
    private String awsRegion;

    @Value(("${jwt.aws.poolId}"))
    private String cognitoUserPoolId;
}
