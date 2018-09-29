package com.donalola.api.authentication.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

import java.io.Serializable;

public interface JwtToken extends Serializable {

    String getToken();

    String getTokenId();

    Jws<Claims> parseClaims(String signingKey);
}
