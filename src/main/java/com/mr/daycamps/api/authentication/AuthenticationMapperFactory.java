package com.mr.daycamps.api.authentication;

import org.springframework.stereotype.Component;

@Component
class AuthenticationMapperFactory {

    private final ParentAuthenticationApiMapper parentAuthenticationMapper;
    private final SchoolAuthenticationApiMapper schoolAuthenticationMapper;

    public AuthenticationMapperFactory(ParentAuthenticationApiMapper parentAuthenticationMapper,
                                       SchoolAuthenticationApiMapper schoolAuthenticationMapper) {
        this.parentAuthenticationMapper = parentAuthenticationMapper;
        this.schoolAuthenticationMapper = schoolAuthenticationMapper;
    }

    AuthenticationApiMapper getMapper(SignUpRequest signUpRequest) {
        return switch (signUpRequest.getRole()) {
            case ROLE_PARENT -> parentAuthenticationMapper;
            case ROLE_SCHOOL -> schoolAuthenticationMapper;
        };
    }
}
