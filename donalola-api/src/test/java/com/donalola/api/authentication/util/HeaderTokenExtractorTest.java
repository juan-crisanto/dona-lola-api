package com.donalola.api.authentication.util;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationServiceException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class HeaderTokenExtractorTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    private HttpServletRequest successHttpServletRequest;
    @Mock
    private HttpServletRequest withoutHeaderHttpServletRequest;
    @Mock
    private HttpServletRequest blankHeaderHttpServletRequest;
    private TokenExtractor tokenExtractor;
    private List<String> applies;

    @org.junit.Before
    public void setUp() throws Exception {
        this.tokenExtractor = new HeaderTokenExtractor();
        Mockito.when(this.successHttpServletRequest.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJkaWVnb2NyaXNhbnRvMDZAZ21haWwuY29tIiwibmFtZSI6Ikp1YW4gRGllZ28gQ3Jpc2FudG8gU2lsdXDDuiIsImlhdCI6MTUxNjIzOTAyMiwic2NvcGVzIjpbIlJPTEVfU0VMTEVSIl19.guzu9GLDqgBrgyNwt9bZcNC5qPGU0CUDjP1_UDMVca0");
        Mockito.when(this.withoutHeaderHttpServletRequest.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(null);
        Mockito.when(this.blankHeaderHttpServletRequest.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(StringUtils.EMPTY);
        applies = new ArrayList<>();
        applies.add("HEADER");
    }

    @Test(expected = AuthenticationServiceException.class)
    public void blankAuthHeader() {
        this.tokenExtractor.extract(blankHeaderHttpServletRequest);
    }

    @Test(expected = AuthenticationServiceException.class)
    public void extractWithNullHeader() {
        this.tokenExtractor.extract(withoutHeaderHttpServletRequest);
    }

    @org.junit.Test
    public void extract() {
        String token = this.tokenExtractor.extract(successHttpServletRequest);
        System.out.println(token);
        Assert.assertNotNull(token);
    }

    @org.junit.Test
    public void extractApplies() {
        Assert.assertTrue(this.tokenExtractor.extractApplies(applies));
    }
}