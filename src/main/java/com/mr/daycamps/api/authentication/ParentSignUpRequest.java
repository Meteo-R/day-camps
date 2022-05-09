package com.mr.daycamps.api.authentication;

import com.mr.daycamps.domain.authentication.Role;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
class ParentSignUpRequest extends SignUpRequest {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    {
        setRole(Role.ROLE_PARENT);
    }

}
