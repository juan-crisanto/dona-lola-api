package com.donalola.api.authentication.util;

import com.donalola.api.config.jwt.JwtConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.security.authentication.AuthenticationServiceException;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

public class TokenExtractorUtilTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    private TokenExtractor noneFoundTokenExtractor;
    @Mock
    private TokenExtractor foundTokenExtractor;
    @Mock
    private JwtConfig jwtConfig;
    @Mock
    private HttpServletRequest httpServletRequest;

    @Before
    public void setUp() throws Exception {
        Mockito.when(jwtConfig.getMustBePresentIn()).thenReturn(Arrays.asList("NONE", "FOUND"));
        Mockito.when(noneFoundTokenExtractor.extract(Mockito.any())).thenReturn(null);
        Mockito.when(foundTokenExtractor.extract(Mockito.any())).thenReturn("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJkaWVnb2NyaXNhbnRvMDZAZ21haWwuY29tIiwibmFtZSI6Ikp1YW4gRGllZ28gQ3Jpc2FudG8gU2lsdXDDuiIsImlhdCI6MTUxNjIzOTAyMiwic2NvcGVzIjpbIlJPTEVfU0VMTEVSIl19.guzu9GLDqgBrgyNwt9bZcNC5qPGU0CUDjP1_UDMVca0");
        Mockito.when(noneFoundTokenExtractor.extractApplies(Mockito.anyList())).thenReturn(true);
        Mockito.when(foundTokenExtractor.extractApplies(Mockito.anyList())).thenReturn(true);
    }

    @Test
    public void extract() {
        TokenExtractorUtil tokenExtractorUtil = new TokenExtractorUtil(Arrays.asList(foundTokenExtractor), this.jwtConfig);
        String token = tokenExtractorUtil.extract(this.httpServletRequest);
        Assert.assertNotNull(token);
    }

    @Test(expected = AuthenticationServiceException.class)
    public void nullTokenExtract() {
        TokenExtractorUtil tokenExtractorUtil = new TokenExtractorUtil(Arrays.asList(noneFoundTokenExtractor), this.jwtConfig);
        tokenExtractorUtil.extract(this.httpServletRequest);
    }
}