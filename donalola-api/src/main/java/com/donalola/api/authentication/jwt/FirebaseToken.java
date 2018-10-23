package com.donalola.api.authentication.jwt;

import com.auth0.jwt.JWT;

import java.util.Optional;

public class FirebaseToken extends AbstractToken {

    private final String token;
    private final String keyId;

    public FirebaseToken(String token) {
        this.token = token;
        this.keyId = JWT.decode(this.token).getKeyId();
    }

    @Override
    public String getToken() {
        return this.token;
    }

    @Override
    public String getKeyId() {
        if (!Optional.ofNullable(this.keyId).isPresent()) {
            throw new IllegalStateException("Empty Key Id");
        }
        return this.keyId;
    }

}
