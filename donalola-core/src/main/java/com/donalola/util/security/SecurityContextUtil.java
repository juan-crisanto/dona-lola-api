package com.donalola.util.security;

import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityContextUtil {

    public static String getLoggedUsername() {
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
