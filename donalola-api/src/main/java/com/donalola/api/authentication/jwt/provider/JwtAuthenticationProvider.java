package com.donalola.api.authentication.jwt.provider;

import com.donalola.api.authentication.jwt.JwtToken;
import com.donalola.api.authentication.jwt.token.JwtAuthenticationToken;
import com.donalola.api.authentication.util.JwtClaimNames;
import com.donalola.api.authentication.util.JwtTokenHelper;
import com.donalola.authentication.AppUser;
import lombok.extern.slf4j.Slf4j;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtTokenHelper jwtTokenHelper;

    public JwtAuthenticationProvider(JwtTokenHelper jwtTokenHelper) {
        this.jwtTokenHelper = jwtTokenHelper;
    }

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtToken rawAccessToken = (JwtToken) authentication.getCredentials();
        JwtClaims jwtClaims = rawAccessToken.parseClaims(this.jwtTokenHelper.getJwtConsumer());
        try {
            AppUser appUser = new AppUser();
            appUser.setUsername(jwtClaims.getSubject());
            appUser.setEmail((String) jwtClaims.getClaimsMap().get(JwtClaimNames.EMAIL));

            List<GrantedAuthority> authorityList = new ArrayList<>();
            authorityList.add(new SimpleGrantedAuthority("ROLE_SELLER"));

            if (log.isDebugEnabled()) {
                log.debug("Success authentication (Principal): " + appUser.getUsername());
            }

            return new JwtAuthenticationToken(appUser.getUsername(), authorityList, appUser);

        } catch (MalformedClaimException e) {
            throw new BadCredentialsException("BadCredentials - cause: " + e.getMessage(), e);
        }
    }

    public boolean supports(Class<?> aClass) {
        return JwtAuthenticationToken.class.isAssignableFrom(aClass);
    }
}
