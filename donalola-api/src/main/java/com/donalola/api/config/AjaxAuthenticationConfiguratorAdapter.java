package com.donalola.api.config;

import com.donalola.api.filter.RequestWrapperFilter;
import com.donalola.api.filter.authentication.AjaxAuthenticationFilter;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.GenericFilterBean;

//@Order(Ordered.HIGHEST_PRECEDENCE + 100)
//@Configuration
public class AjaxAuthenticationConfiguratorAdapter extends BaseSecurityConfiguratorAdapter {

    public static final String AJAX_BASED_ENTRY_POINT = "/api/auth/**";
    public static final String AJAX_BASED_LOGIN_ENTRY_POINT = "/api/auth/login";
    public static final String AJAX_BASED_LOGOUT_ENTRY_POINT = "/api/auth/logout";

    protected GenericFilterBean buildAuthenticationFilter() throws Exception {
        AjaxAuthenticationFilter filter = new AjaxAuthenticationFilter(AJAX_BASED_LOGIN_ENTRY_POINT);
        filter.setAuthenticationManager(authenticationManager());
        return filter;
    }

    protected GenericFilterBean buildRequestWrapperFilter() {
        return new RequestWrapperFilter(new AntPathRequestMatcher(AJAX_BASED_LOGIN_ENTRY_POINT));
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http.authorizeRequests()
                .antMatchers(AJAX_BASED_ENTRY_POINT).permitAll()
                .antMatchers(AJAX_BASED_LOGIN_ENTRY_POINT).permitAll()
                .antMatchers(AJAX_BASED_LOGOUT_ENTRY_POINT).permitAll()
                .and()
                .addFilterAfter(buildRequestWrapperFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(buildAuthenticationFilter(), RequestWrapperFilter.class)
                .logout()
                .logoutUrl(AJAX_BASED_LOGOUT_ENTRY_POINT);
    }

    protected String getUrlEntryPoint() {
        return AJAX_BASED_ENTRY_POINT;
    }

    protected AuthenticationProvider getAuthenticationProvider() {
        return null;
    }
}
