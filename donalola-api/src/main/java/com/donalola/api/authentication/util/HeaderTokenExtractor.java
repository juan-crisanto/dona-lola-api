package com.donalola.api.authentication.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Component
public class HeaderTokenExtractor implements TokenExtractor {

    public static final String HEADER_PREFIX = "Bearer ";
    public static final String PRESENT_KEY = "HEADER";


    @Override
    public String extract(HttpServletRequest request) {

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header == null) {
            throw new AuthenticationServiceException("Authorization header cannot be blank!");
        }

        if (header.length() < HEADER_PREFIX.length()) {
            throw new AuthenticationServiceException("Invalid authorization header size.");
        }

        return header.substring(HEADER_PREFIX.length(), header.length());
    }

    @Override
    public boolean extractApplies(List<String> keys) {
        return keys.parallelStream().anyMatch(s -> s.equals(PRESENT_KEY));
    }
}
