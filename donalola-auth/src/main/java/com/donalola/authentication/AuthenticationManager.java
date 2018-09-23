package com.donalola.authentication;

import com.donalola.authentication.dto.AuthenticationResponseDto;

public interface AuthenticationManager {

    AuthenticationResponseDto authenticateWithUsernameAndPassword(String username, String password);

    AuthenticationResponseDto authenticateWithOAuth(String token);

}
