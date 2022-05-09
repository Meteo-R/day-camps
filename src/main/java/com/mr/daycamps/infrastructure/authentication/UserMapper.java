package com.mr.daycamps.infrastructure.authentication;

import com.mr.daycamps.domain.authentication.User;

abstract class UserMapper {

    abstract User mapUser(UserEntity userEntity);

    abstract UserEntity mapUser(User user);

}
