package com.donalola.api.config;

import com.donalola.api.ApiAuthenticationEntyPoint;
import com.donalola.api.filter.cors.CustomCorsFilter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.filter.GenericFilterBean;

public abstract class BaseSecurityConfiguratorAdapter extends WebSecurityConfigurerAdapter {

    /*
    Configuring Security Authentication Manager
     */

    @Setter(onMethod = @__(@Autowired))
    private CustomCorsFilter customCorsFilter;

    @Setter(onMethod = @__(@Autowired))
    private ApiAuthenticationEntyPoint apiAuthenticationEntyPoint;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(getAuthenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher(getUrlEntryPoint())
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(getAuthenticationEntryPoint())
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(getCorsFilter(), CorsFilter.class);
    }

    protected abstract GenericFilterBean buildAuthenticationFilter() throws Exception;

    protected abstract String getUrlEntryPoint();

    protected AuthenticationEntryPoint getAuthenticationEntryPoint() {
        return this.apiAuthenticationEntyPoint;
    }

    protected CorsFilter getCorsFilter() {
        return this.customCorsFilter;
    }

    protected abstract AuthenticationProvider getAuthenticationProvider();

}
