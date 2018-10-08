package com.donalola.api.config;

import java.util.List;

public interface CorsConfig {

    List<String> getExposedHeaders();

    Long getMaxAge();

    List<String> getAllowedMethods();

    List<String> getAllowedOrigins();

    List<String> getAllowedHeaders();

    Boolean getAllowCredentials();

    String getPath();

}
