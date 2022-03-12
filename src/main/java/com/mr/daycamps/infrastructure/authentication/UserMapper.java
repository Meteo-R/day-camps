package com.mr.daycamps.infrastructure.authentication;

import com.mr.daycamps.domain.authentication.User;
import org.springframework.stereotype.Component;

@Component
class UserMapper {

    User mapUser(UserEntity userEntity) {
        return User.builder()
                .setUsername(userEntity.getUsername())
                .setEmail(userEntity.getEmail())
                .setPassword(userEntity.getPassword())
                .setRole(userEntity.getRole())
                .build();
    }

    UserEntity mapUser(User user) {
        return UserEntity.builder()
                .setUsername(user.getUsername())
                .setEmail(user.getEmail())
                .setPassword(user.getPassword())
                .setRole(user.getRole())
                .build();
    }
}
