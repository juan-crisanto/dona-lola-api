package com.donalola.api.authentication.util;

import com.donalola.api.authentication.jwt.JwtToken;
import org.jose4j.jwt.consumer.JwtConsumer;

public interface JwtTokenHelper {

    JwtConsumer getJwtConsumer(JwtToken jwtToken);

}
