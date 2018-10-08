package com.donalola.api.authentication.jwt;

import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.JwtConsumer;

import java.io.Serializable;

public interface JwtToken extends Serializable {

    String getToken();

    String getKeyId();

    JwtClaims parseClaims(JwtConsumer jwtConsumer);
}
