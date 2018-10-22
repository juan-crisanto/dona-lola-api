package com.donalola.application.util;

import com.donalola.authentication.AppUser;
import com.donalola.util.security.SecurityContextUtil;
import org.springframework.security.core.Authentication;

public class SecurityApplicationUtil {

    public static AppUser getUser() {
        Authentication authentication = SecurityContextUtil.getAuthentication();
        return (AppUser) authentication.getDetails();
    }
}
