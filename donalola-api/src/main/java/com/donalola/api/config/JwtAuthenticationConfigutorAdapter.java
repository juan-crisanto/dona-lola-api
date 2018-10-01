package com.donalola.api.config;

import com.donalola.api.authentication.util.TokenExtractorUtil;
import com.donalola.api.filter.authentication.JwtTokenAccessAuthenticationFilter;
import com.donalola.api.util.matcher.SkiptPathRequestMatcher;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.filter.GenericFilterBean;

import java.util.Arrays;
import java.util.List;

@Order(Ordered.HIGHEST_PRECEDENCE + 101)
@Configuration
public class JwtAuthenticationConfigutorAdapter extends BaseSecurityConfiguratorAdapter {

    public static final String TOKEN_BASED_AUTH_ENTRY_POINT = "/api/**";
    public static final String TOKEN_REFRESH_ENTRY_POINT = "/api/auth/token";
    public static final String TOKEN_BASED_EXCLUDED_PATH = "/api/excluded/**";

    @Setter(onMethod = @__(@Autowired))
    private TokenExtractorUtil tokenExtractorUtil;

    protected GenericFilterBean buildAuthenticationFilter() throws Exception {
        List<String> pathsToSkip = Arrays.asList(TOKEN_BASED_EXCLUDED_PATH, TOKEN_REFRESH_ENTRY_POINT);
        SkiptPathRequestMatcher skiptPathRequestMatcher = new SkiptPathRequestMatcher(pathsToSkip, TOKEN_BASED_EXCLUDED_PATH);
        JwtTokenAccessAuthenticationFilter filter = new JwtTokenAccessAuthenticationFilter(skiptPathRequestMatcher, this.tokenExtractorUtil);
        filter.setAuthenticationManager(authenticationManager());
        return filter;
    }

    protected String getUrlEntryPoint() {
        return TOKEN_BASED_AUTH_ENTRY_POINT;
    }

    protected AuthenticationProvider getAuthenticationProvider() {
        return null;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http.authorizeRequests()
                .antMatchers(TOKEN_REFRESH_ENTRY_POINT).permitAll()
                .antMatchers(TOKEN_BASED_EXCLUDED_PATH).permitAll()
                .antMatchers(TOKEN_BASED_AUTH_ENTRY_POINT).authenticated();
    }
}
