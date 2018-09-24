package com.donalola.api.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Component
public class ApiCorsConfig implements CorsConfig {


    @Value("#{'${cors.exposedHeaders}'.split(',')}")
    private List<String> exposedHeaders;

    @Value("${cors.maxAge}")
    private Long maxAge;

    @Value("#{'${cors.allowedMethods}'.split(',')}")
    private List<String> allowedMethods;

    @Value("#{'${cors.allowedOrigins}'.split(',')}")
    private List<String> allowedOrigins;

    @Value("#{'${cors.allowedHeaders}'.split(',')}")
    private List<String> allowedHeaders;

    @Value("${cors.allow-credentials:false}")
    private Boolean allowCredentials;

    @Value("${cors.path:/**}")
    private String path;
}
