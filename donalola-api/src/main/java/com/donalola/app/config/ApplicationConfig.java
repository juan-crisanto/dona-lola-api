package com.donalola.app.config;

import org.springframework.context.annotation.*;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;

//@SpringBootApplication
@EnableGlobalMethodSecurity(jsr250Enabled = true, securedEnabled = true)
@EnableWebMvc
@EnableWebSecurity
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
@ComponentScan(basePackages = "com.donalola")
@PropertySources({
        @PropertySource("classpath:cors.properties"),
        @PropertySource("classpath:jwt.properties"),
        @PropertySource("classpath:dao.properties")
})
public class ApplicationConfig {

    @Bean
    public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
        return new RequestMappingHandlerMapping() {

            private static final String API_BASE_PATH = "/api";

            @Override
            protected void registerHandlerMethod(Object handler, Method method, RequestMappingInfo mapping) {
                Class<?> beanType = method.getDeclaringClass();
                if (AnnotationUtils.findAnnotation(beanType, RestController.class) != null) {
                    PatternsRequestCondition apiPattern = new PatternsRequestCondition(API_BASE_PATH).combine(mapping.getPatternsCondition());

                    mapping = new RequestMappingInfo(mapping.getName(), apiPattern, mapping.getMethodsCondition(), mapping.getParamsCondition(), mapping.getHeadersCondition(), mapping.getConsumesCondition(), mapping.getProducesCondition(), mapping.getCustomCondition());
                }

                super.registerHandlerMethod(handler, method, mapping);
            }
        };
    }
}
