package com.mr.daycamps.api.authentication;

import org.springframework.stereotype.Component;

@Component
class AuthenticationMapperFactory {

    private final ParentAuthenticationMapper parentAuthenticationMapper;
    private final SchoolAuthenticationMapper schoolAuthenticationMapper;

    public AuthenticationMapperFactory(ParentAuthenticationMapper parentAuthenticationMapper,
                                       SchoolAuthenticationMapper schoolAuthenticationMapper) {
        this.parentAuthenticationMapper = parentAuthenticationMapper;
        this.schoolAuthenticationMapper = schoolAuthenticationMapper;
    }

    AuthenticationMapper getMapper(SignUpRequest signUpRequest) {
        return switch (signUpRequest.getRole()) {
            case ROLE_PARENT -> parentAuthenticationMapper;
            case ROLE_SCHOOL -> schoolAuthenticationMapper;
        };
    }
}
