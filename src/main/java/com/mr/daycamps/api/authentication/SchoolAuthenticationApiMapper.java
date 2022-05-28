package com.mr.daycamps.api.authentication;

import com.mr.daycamps.domain.authentication.School;
import com.mr.daycamps.domain.authentication.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
class SchoolAuthenticationApiMapper extends AuthenticationApiMapper {
    public SchoolAuthenticationApiMapper(PasswordEncoder passwordEncoder) {
        super(passwordEncoder);
    }

    @Override
    User mapToUser(SignUpRequest signUpRequest) {
        SchoolSignUpRequest schoolSignUpRequest = (SchoolSignUpRequest) signUpRequest;
        return School.builder()
                .setUsername(schoolSignUpRequest.getUsername())
                .setEmail(schoolSignUpRequest.getEmail())
                .setPhone(schoolSignUpRequest.getPhone())
                .setPassword(passwordEncoder.encode(schoolSignUpRequest.getPassword()))
                .setRole(schoolSignUpRequest.getRole())
                .setName(schoolSignUpRequest.getName())
                .setAddress(schoolSignUpRequest.getAddress())
                .build();
    }
}
