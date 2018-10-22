package com.donalola.application;


import com.donalola.application.config.ApplicationConfig;
import com.donalola.application.config.SwaggerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Import;

@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, SecurityAutoConfiguration.class})
@Import({ApplicationConfig.class, SwaggerConfig.class})
public class Application {

    public static void main(String... args) {
        SpringApplication.run(Application.class);
    }
}
