package com.donalola.app.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

//@SpringBootApplication
//@EnableGlobalMethodSecurity(jsr250Enabled = true, securedEnabled = true)
//@EnableWebMvc
@EnableWebSecurity
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
@ComponentScan(basePackages = "com.donalola")
@PropertySources({
        @PropertySource("classpath:cors.properties"),
        @PropertySource("classpath:jwt.properties"),
        @PropertySource("classpath:dao.properties"),
        @PropertySource("classpath:dynamodb.properties")
})
public class ApplicationConfig {

}
