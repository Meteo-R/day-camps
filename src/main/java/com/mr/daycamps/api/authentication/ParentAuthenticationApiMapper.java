package com.mr.daycamps.api.authentication;

import com.mr.daycamps.domain.authentication.Parent;
import com.mr.daycamps.domain.authentication.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
class ParentAuthenticationApiMapper extends AuthenticationApiMapper {
    public ParentAuthenticationApiMapper(PasswordEncoder passwordEncoder) {
        super(passwordEncoder);
    }

    @Override
    User mapToUser(SignUpRequest signUpRequest) {
        ParentSignUpRequest parentSignUpRequest = (ParentSignUpRequest) signUpRequest;
        return Parent.builder()
                .setUsername(parentSignUpRequest.getUsername())
                .setEmail(parentSignUpRequest.getEmail())
                .setPhone(parentSignUpRequest.getPhone())
                .setPassword(passwordEncoder.encode(parentSignUpRequest.getPassword()))
                .setRole(parentSignUpRequest.getRole())
                .setFirstName(parentSignUpRequest.getFirstName())
                .setLastName(parentSignUpRequest.getLastName())
                .build();
    }
}
