package com.donalola.api.authentication.jwt.provider;

import com.donalola.api.authentication.jwt.JwtToken;
import com.donalola.api.authentication.jwt.token.JwtAuthenticationToken;
import com.donalola.api.authentication.util.JwtClaimNames;
import com.donalola.api.authentication.util.JwtTokenHelper;
import com.donalola.authentication.AppUser;
import org.jose4j.jwt.JwtClaims;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.security.authentication.AuthenticationProvider;

import java.util.Map;

public class JwtAuthenticationProviderTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    private AuthenticationProvider jwtAuthenticationProvider;
    @Mock
    private JwtTokenHelper jwtTokenHelper;
    @Mock
    private JwtToken jwtToken;
    @Mock
    private JwtClaims validJwtClaims;
    @Mock
    private Map<String, Object> claimsObject;


    @Before
    public void setUp() throws Exception {
        Mockito.when(jwtTokenHelper.getJwtConsumer(Mockito.any())).thenReturn(Mockito.any());
        Mockito.when(claimsObject.get(JwtClaimNames.EMAIL)).thenReturn("diegocrisanto06@gmail.com");
        Mockito.when(validJwtClaims.getSubject()).thenReturn("e62aea8b-37ca-474b-9ed7-853f3b05ecae");
        Mockito.when(validJwtClaims.getClaimsMap()).thenReturn(claimsObject);
        Mockito.when(jwtToken.parseClaims(Mockito.any())).thenReturn(this.validJwtClaims);
        this.jwtAuthenticationProvider = new JwtAuthenticationProvider(jwtTokenHelper);
    }

    @Test
    public void authenticate() {
        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) this.jwtAuthenticationProvider.authenticate(new JwtAuthenticationToken(jwtToken));
        Assert.assertNotNull(jwtAuthenticationToken);
        Assert.assertEquals("e62aea8b-37ca-474b-9ed7-853f3b05ecae", jwtAuthenticationToken.getPrincipal());
        Assert.assertTrue(AppUser.class.isAssignableFrom(jwtAuthenticationToken.getDetails().getClass()));
    }

    @Test
    public void supports() {
        Assert.assertTrue(jwtAuthenticationProvider.supports(JwtAuthenticationToken.class));

    }
}