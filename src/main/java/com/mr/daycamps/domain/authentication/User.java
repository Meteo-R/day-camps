package com.mr.daycamps.domain.authentication;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(setterPrefix = "set")
public class User {
    private String username;
    private String email;
    private String password;
    private Role role;
}
