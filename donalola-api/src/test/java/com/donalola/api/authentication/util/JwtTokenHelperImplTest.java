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
    private static final String id_token = "eyJraWQiOiI1WWxJT2FhUVlGK1k5eCtaN2FhcHVYYnlaVUc1MmVQVXhiSWRVSnZRRVZnPSIsImFsZyI6IlJTMjU2In0.eyJhdF9oYXNoIjoiazhsZi1DZTA2MXhLTTk1clkyTWl5ZyIsInN1YiI6ImU2MmFlYThiLTM3Y2EtNDc0Yi05ZWQ3LTg1M2YzYjA1ZWNhZSIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJnZW5kZXIiOiJNIiwiaXNzIjoiaHR0cHM6XC9cL2NvZ25pdG8taWRwLnVzLWVhc3QtMS5hbWF6b25hd3MuY29tXC91cy1lYXN0LTFfRmRwU3hZRXFWIiwiY29nbml0bzp1c2VybmFtZSI6ImU2MmFlYThiLTM3Y2EtNDc0Yi05ZWQ3LTg1M2YzYjA1ZWNhZSIsImdpdmVuX25hbWUiOiJKdWFuIiwiYXVkIjoiN2MybTFtdDMzcHFzczQ5amI5MDEzajBqcDMiLCJldmVudF9pZCI6IjI4MzFkNmM0LWNhOTYtMTFlOC1hM2I0LTliZDkwZGNhMjQwNCIsInRva2VuX3VzZSI6ImlkIiwiYXV0aF90aW1lIjoxNTM4OTYwNjkwLCJwaG9uZV9udW1iZXIiOiIrNTE5NzM4NjQzOTIiLCJleHAiOjE1Mzg5NjQyOTAsImlhdCI6MTUzODk2MDY5MCwiZmFtaWx5X25hbWUiOiJDcmlzYW50byIsImVtYWlsIjoiZGllZ29jcmlzYW50bzA2QGdtYWlsLmNvbSJ9.X8CgAJN8qF_jRBdDy1-0H_PJIiLmYa-358qGWFYMJ-gedQXrtBDKrsISkzcTGw0qH9P6UIpP8aRmOqBIoMkFd7K6fsRpVme0LQmtc21pqA7K4kDAdyKWHQ53Mb3Zt7qrsz0Ty4vctc1txb46LuMiMtSj--9pfajgfMMimVDpGl45UkbP778VyubYE11U9FIqinSGeb8lMEtJd8j5ZFXh3_uim2MS_KIrzUkWdiL255iEoms-aB4aJREziI81mEZZD8fFMGcdbUq6hqA7qy0vvE7bORj7oY7Ycxs0D283x_vnQqjnncvK1Zr7N-tkd_bRaWWBH14SfmztF9t9wQ5_Sg";
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