package com.donalola.app.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

//@SpringBootApplication
@EnableGlobalMethodSecurity(jsr250Enabled = true, securedEnabled = true)
@EnableWebMvc
@EnableWebSecurity
@Configuration
@ComponentScan(basePackages = "com.donalola")
@PropertySources({
        @PropertySource("classpath:cors.properties")
})
public class ApplicationConfig {
}
