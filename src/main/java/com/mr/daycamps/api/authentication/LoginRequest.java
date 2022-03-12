package com.mr.daycamps.api.authentication;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
class LoginRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
