package com.donalola.api.authentication.jwt.token;

import com.donalola.api.authentication.jwt.JwtToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class JwtAuthenticationToken implements Authentication {

    private static final long serialVersionUID = -8603101134106883944L;

    private boolean authenticated;
    private Optional<JwtToken> rawAccessToken;
    private final List<GrantedAuthority> authorityList;
    private final Optional<UserDetails> authenticatedUser;
    private String principal;

    public JwtAuthenticationToken(JwtToken jwtToken) {
        this.authenticated = false;
        this.rawAccessToken = Optional.of(jwtToken);
        this.authorityList = new ArrayList<>();
        this.authenticatedUser = Optional.empty();
    }

    public JwtAuthenticationToken(String principal, List<GrantedAuthority> authorityList, UserDetails authenticatedUser) {
        this.authenticated = true;
        this.rawAccessToken = Optional.empty();
        this.authenticatedUser = Optional.of(authenticatedUser);
        if (!this.authenticatedUser.isPresent()) {
            throw new IllegalStateException();
        }
        this.principal = principal;
        this.authorityList = authorityList;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorityList;
    }

    public Object getCredentials() {
        if (this.rawAccessToken.isPresent()) {
            return this.rawAccessToken.get();
        }
        throw new IllegalStateException();
    }

    public Object getDetails() {
        return this.authenticatedUser.get();
    }

    public Object getPrincipal() {
        return this.principal;
    }

    public boolean isAuthenticated() {
        return this.authenticated;
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        throw new IllegalArgumentException("Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
    }

    public String getName() {
        return this.principal;
    }
}
