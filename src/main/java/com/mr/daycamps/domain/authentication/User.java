package com.mr.daycamps.domain.authentication;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(setterPrefix = "set")
public abstract class User {
    protected String username;
    protected String email;
    protected String phone;
    protected String password;
    protected Role role;
}
