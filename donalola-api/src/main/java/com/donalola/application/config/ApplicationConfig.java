package com.donalola.application.config;

import org.springframework.context.annotation.*;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

//@SpringBootApplication

//@EnableGlobalMethodSecurity(jsr250Enabled = true, securedEnabled = true)
//@EnableWebMvc
@Configuration
@EnableWebSecurity
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
@ComponentScan(basePackages = "com.donalola")
@PropertySources({
        @PropertySource("classpath:cors.properties"),
        @PropertySource("classpath:jwt.properties"),
        @PropertySource("classpath:dao.properties"),
        @PropertySource("classpath:dynamodb.properties")
})
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class ApplicationConfig {

}
