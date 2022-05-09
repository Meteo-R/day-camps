package com.mr.daycamps.api.authentication;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.mr.daycamps.domain.authentication.Role;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
class SchoolSignUpRequest extends SignUpRequest {

    @NotBlank
    @JsonAlias("schoolName")
    protected String name;

    @NotBlank
    @JsonAlias("schoolAddress")
    protected String address;

    {
        setRole(Role.ROLE_SCHOOL);
    }

}
