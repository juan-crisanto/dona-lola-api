package com.donalola.authentication.dto;

import com.donalola.authentication.AppUser;

public interface AuthenticationResponseDto {

    AppUser getAuthenticatedUser();

}
