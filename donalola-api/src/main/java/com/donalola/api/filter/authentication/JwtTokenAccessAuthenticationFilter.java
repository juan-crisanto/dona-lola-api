package com.donalola.api.filter.authentication;

import com.donalola.api.authentication.jwt.AWSCognitoToken;
import com.donalola.api.authentication.jwt.JwtToken;
import com.donalola.api.authentication.jwt.token.JwtAuthenticationToken;
import com.donalola.api.authentication.util.TokenExtractorUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtTokenAccessAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final TokenExtractorUtil tokenExtractorUtil;

    public JwtTokenAccessAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher, TokenExtractorUtil tokenExtractorUtil) {
        super(requiresAuthenticationRequestMatcher);
        this.tokenExtractorUtil = tokenExtractorUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String token = this.tokenExtractorUtil.extract(request);
        JwtToken rawJwtToken = new AWSCognitoToken(token);
        return this.getAuthenticationManager().authenticate(new JwtAuthenticationToken(rawJwtToken));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authResult);
        SecurityContextHolder.setContext(context);
        chain.doFilter(request, response);
    }
}
