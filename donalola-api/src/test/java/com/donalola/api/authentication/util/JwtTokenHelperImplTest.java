package com.donalola.api.authentication.util;

import com.donalola.api.authentication.jwt.AWSCognitoToken;
import com.donalola.api.authentication.jwt.JwtToken;
import com.donalola.api.config.jwt.JwtConfig;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.security.authentication.BadCredentialsException;

public class JwtTokenHelperImplTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    private JwtToken expiredAccessToken;
    private JwtToken validIdToken;
    private static final String access_token = "eyJraWQiOiJUblwvQjlMdHNVNjJIVUtNMXhnVWJcL0Q4VkpYWUNqbTZNS1VyeDExWVI2WmM9IiwiYWxnIjoiUlMyNTYifQ.eyJzdWIiOiJlNjJhZWE4Yi0zN2NhLTQ3NGItOWVkNy04NTNmM2IwNWVjYWUiLCJldmVudF9pZCI6IjFiNGEzM2EyLWNhOGMtMTFlOC04ZmJlLTM5M2M5N2FkNzk5NSIsInRva2VuX3VzZSI6ImFjY2VzcyIsInNjb3BlIjoiYXdzLmNvZ25pdG8uc2lnbmluLnVzZXIuYWRtaW4gcGhvbmUgb3BlbmlkIHByb2ZpbGUgZW1haWwiLCJhdXRoX3RpbWUiOjE1Mzg5NTYzNzMsImlzcyI6Imh0dHBzOlwvXC9jb2duaXRvLWlkcC51cy1lYXN0LTEuYW1hem9uYXdzLmNvbVwvdXMtZWFzdC0xX0ZkcFN4WUVxViIsImV4cCI6MTUzODk1OTk3MywiaWF0IjoxNTM4OTU2MzczLCJ2ZXJzaW9uIjoyLCJqdGkiOiJmZTEyYjY3NC00NTM4LTRhNTgtOGMxNy1lYjk5MTAxNDAyOGMiLCJjbGllbnRfaWQiOiI3YzJtMW10MzNwcXNzNDlqYjkwMTNqMGpwMyIsInVzZXJuYW1lIjoiZTYyYWVhOGItMzdjYS00NzRiLTllZDctODUzZjNiMDVlY2FlIn0.W-REebDKW9WYz4AiBs6bENs-ayj62Rn5AbhJWlfx2RpX8RQfSEua-PxBahkJsknfDLoyzkV45pYwCEPIF9D2dybmMUPkmHHpRGqfeHfcwVIOY6lORjs23XlJhkl2jJx1ZC10ZOl_UahXg7mRC5lKvTtT7mqCDym3S-QpROACCozQM91PFc0zzRXJ5asAVuk9JCILwJMQr1OlNu61ymocNVAa2fVfP7W_Fw6DnYWVfGR6BM-ZFRsphFXXL6qwFMx9fo-Fm3zR7jm0AjNbYlPoAJS0Y_7R3BTVXTa8m7DZMnnhik5SofGNlG6Q3jdm3cXN3hK-T80_tQxem7_hMcm-qw";
    private static final String id_token = "eyJraWQiOiI1WWxJT2FhUVlGK1k5eCtaN2FhcHVYYnlaVUc1MmVQVXhiSWRVSnZRRVZnPSIsImFsZyI6IlJTMjU2In0.eyJhdF9oYXNoIjoidWR6OUZCMDc4dDBxR3BqNzNrVDlpZyIsInN1YiI6ImU2MmFlYThiLTM3Y2EtNDc0Yi05ZWQ3LTg1M2YzYjA1ZWNhZSIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJnZW5kZXIiOiJNIiwiaXNzIjoiaHR0cHM6XC9cL2NvZ25pdG8taWRwLnVzLWVhc3QtMS5hbWF6b25hd3MuY29tXC91cy1lYXN0LTFfRmRwU3hZRXFWIiwiY29nbml0bzp1c2VybmFtZSI6ImU2MmFlYThiLTM3Y2EtNDc0Yi05ZWQ3LTg1M2YzYjA1ZWNhZSIsImdpdmVuX25hbWUiOiJKdWFuIiwiYXVkIjoiN2MybTFtdDMzcHFzczQ5amI5MDEzajBqcDMiLCJldmVudF9pZCI6IjRmZWQ3MjY5LWNmZTItMTFlOC1hNjhkLWM5ZThmMGExNjBhZiIsInRva2VuX3VzZSI6ImlkIiwiYXV0aF90aW1lIjoxNTM5NTQzMTU0LCJwaG9uZV9udW1iZXIiOiIrNTE5NzM4NjQzOTIiLCJleHAiOjE1Mzk1NDY3NTQsImlhdCI6MTUzOTU0MzE1NCwiZmFtaWx5X25hbWUiOiJDcmlzYW50byIsImVtYWlsIjoiZGllZ29jcmlzYW50bzA2QGdtYWlsLmNvbSJ9.KhLObgsVs9lsVbHoXl5SKiGfBB2mw7_Zc8NvH4wzqi0iBcCYP7R2WyADZHK2Tdaa783AKyt2PR5zn9NVRaaPTs01nioTgoKInz5hE4lFLvu2Uef5jz5wO06GnDkbZXiMnOTp4x5Rom-2JnjWbGyjPGpILoV-ZUnhOHG1GxtOEQsTBK1RFrMVqQRb1AIiPwVdg9n0uF5WFJlJSSNdJxdFASqzI3zA5rg1l6NMmFkXFjtxZ-Q8KCZ4XVsSabsrQgPtSkLnLm1bd7Grq9u2vMgDzHn0chiV8X4e9OlBI6pMWg-10OMuLfG4esPmrrCYafnmcaWcq0gL_NC1kjmKmMP5fw";
    private JwtTokenHelper jwtTokenHelper;
    @Mock
    private JwtConfig jwtConfig;

    @Before
    public void setUp() throws Exception {
        this.expiredAccessToken = new AWSCognitoToken(access_token);
        this.validIdToken = new AWSCognitoToken(id_token);
        Mockito.when(this.jwtConfig.getAwsRegion()).thenReturn("us-east-1");
        Mockito.when(this.jwtConfig.getCognitoUserPoolId()).thenReturn("us-east-1_FdpSxYEqV");
        Mockito.when(this.jwtConfig.getTokenIssuer()).thenReturn("https://cognito-idp.us-east-1.amazonaws.com/us-east-1_FdpSxYEqV");
        this.jwtTokenHelper = new JwtTokenHelperImpl(this.jwtConfig);
    }

    @Test(expected = BadCredentialsException.class)
    //@Test
    public void verifyIdToken() {
        JwtConsumer jwtConsumer = this.jwtTokenHelper.getJwtConsumer();
        Assert.assertNotNull(jwtConsumer);
        JwtClaims jwtClaims = this.validIdToken.parseClaims(jwtConsumer);
    }

    @Test(expected = BadCredentialsException.class)
    public void verifyAccessToken() {
        JwtConsumer jwtConsumer = this.jwtTokenHelper.getJwtConsumer();
        Assert.assertNotNull(jwtConsumer);
        JwtClaims jwtClaims = this.expiredAccessToken.parseClaims(jwtConsumer);
    }

}