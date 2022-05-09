package com.mr.daycamps.api.authentication;

import com.mr.daycamps.domain.authentication.User;
import org.springframework.security.crypto.password.PasswordEncoder;

abstract class AuthenticationMapper {

    protected final PasswordEncoder passwordEncoder;

    public AuthenticationMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    abstract User mapToUser(SignUpRequest signUpRequest);
}
