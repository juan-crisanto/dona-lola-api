package com.donalola.api.authentication.provider;

import com.donalola.api.authentication.token.JsonUsernamePasswordAuthenticationToken;
import com.donalola.authentication.ApiAuthenticationManager;
import com.donalola.authentication.dto.AuthenticationResponseDto;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


@Component
public class UsernameAndPasswordAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    private final ApiAuthenticationManager apiAuthenticationManager;

    public UsernameAndPasswordAuthenticationProvider(ApiAuthenticationManager apiAuthenticationManager) {
        this.apiAuthenticationManager = apiAuthenticationManager;
    }

    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

    }

    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        JsonUsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (JsonUsernamePasswordAuthenticationToken) authentication;
        AuthenticationResponseDto authenticationResponseDto = this.apiAuthenticationManager.authenticateWithUsernameAndPassword((String) usernamePasswordAuthenticationToken.getPrincipal(), (String) usernamePasswordAuthenticationToken.getCredentials());
        return authenticationResponseDto.getAuthenticatedUser();
    }
}
