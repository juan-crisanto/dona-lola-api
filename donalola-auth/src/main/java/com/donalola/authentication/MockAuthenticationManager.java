package com.donalola.authentication;

import com.donalola.authentication.dto.AuthenticationResponseDto;
import org.springframework.stereotype.Component;

@Component
public class MockAuthenticationManager implements ApiAuthenticationManager {

    public AuthenticationResponseDto authenticateWithUsernameAndPassword(String username, String password) {
        return null;
    }

    public AuthenticationResponseDto authenticateWithOAuth(String token) {
        return null;
    }
}
