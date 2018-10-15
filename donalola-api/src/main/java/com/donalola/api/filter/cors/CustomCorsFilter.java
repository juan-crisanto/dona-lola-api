package com.donalola.api.filter.cors;

import com.donalola.api.config.CorsConfig;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Component
public class CustomCorsFilter extends CorsFilter {

    public CustomCorsFilter(CorsConfig corsConfig) {
        super(corsConfigurationSource(corsConfig));
    }

    private static CorsConfigurationSource corsConfigurationSource(CorsConfig corsConfig) {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(corsConfig.getAllowCredentials());
        config.setAllowedOrigins(corsConfig.getAllowedOrigins());
        config.setAllowedHeaders(corsConfig.getAllowedHeaders());
        config.setExposedHeaders(corsConfig.getExposedHeaders());
        config.setMaxAge(corsConfig.getMaxAge());
        config.setAllowedMethods(corsConfig.getAllowedMethods());
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(corsConfig.getPath(), config);
        return source;
    }
}
