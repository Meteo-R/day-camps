package com.mr.daycamps.api.authentication;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.mr.daycamps.domain.authentication.Role;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "role")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ParentSignUpRequest.class, name = "ROLE_PARENT"),
        @JsonSubTypes.Type(value = SchoolSignUpRequest.class, name = "ROLE_SCHOOL")
})
abstract class SignUpRequest {

    @NotBlank
    @Size(min = 3, max = 20)
    protected String username;

    @NotBlank
    @Size(max = 50)
    @Email
    protected String email;

    @NotBlank
    protected String phone;

    @NotBlank
    @Size(min = 6)
    protected String password;

    @NotNull
    protected Role role;

}
