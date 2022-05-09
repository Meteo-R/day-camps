package com.mr.daycamps.infrastructure.authentication;

import com.mr.daycamps.domain.authentication.User;
import org.springframework.stereotype.Component;

@Component
class UserMapperFactory {

    private final ParentMapper parentMapper;
    private final SchoolMapper schoolMapper;

    public UserMapperFactory(ParentMapper parentMapper, SchoolMapper schoolMapper) {
        this.parentMapper = parentMapper;
        this.schoolMapper = schoolMapper;
    }

    UserMapper getMapper(UserEntity userEntity) {
        return switch (userEntity.getRole()) {
            case ROLE_PARENT -> parentMapper;
            case ROLE_SCHOOL -> schoolMapper;
        };
    }

    UserMapper getMapper(User user) {
        return switch (user.getRole()) {
            case ROLE_PARENT -> parentMapper;
            case ROLE_SCHOOL -> schoolMapper;
        };
    }

}
