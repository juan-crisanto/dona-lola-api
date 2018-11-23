package com.donalola.api.authentication.userandpassword.provider;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


@Component
public class UsernameAndPasswordAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

    }

    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        /*
        JsonUsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (JsonUsernamePasswordAuthenticationToken) authentication;
        AuthenticationResponseDto authenticationResponseDto = this.apiAuthenticationManager.authenticateWithUsernameAndPassword((String) usernamePasswordAuthenticationToken.getPrincipal(), (String) usernamePasswordAuthenticationToken.getCredentials());
        return authenticationResponseDto.getAuthenticatedUser();
        */
        return null;
    }
}
