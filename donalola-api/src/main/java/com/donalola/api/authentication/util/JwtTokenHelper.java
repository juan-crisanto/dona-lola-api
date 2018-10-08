package com.donalola.api.authentication.util;

import org.jose4j.jwt.consumer.JwtConsumer;

public interface JwtTokenHelper {

    JwtConsumer getJwtConsumer();

}
