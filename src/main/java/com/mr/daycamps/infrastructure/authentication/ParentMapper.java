package com.mr.daycamps.infrastructure.authentication;

import com.mr.daycamps.domain.authentication.Parent;
import com.mr.daycamps.domain.authentication.User;
import com.mr.daycamps.infrastructure.users.ParentEntity;
import org.springframework.stereotype.Component;

@Component
class ParentMapper extends UserMapper {

    @Override
    User mapUser(UserEntity userEntity) {
        ParentEntity parentEntity = (ParentEntity) userEntity;
        return Parent.builder()
                .setUsername(parentEntity.getUsername())
                .setEmail(parentEntity.getEmail())
                .setPhone(parentEntity.getPhone())
                .setPassword(parentEntity.getPassword())
                .setRole(parentEntity.getRole())
                .setFirstName(parentEntity.getFirstName())
                .setLastName(parentEntity.getLastName())
                .build();
    }

    @Override
    UserEntity mapUser(User user) {
        Parent parent = (Parent) user;
        return ParentEntity.builder()
                .setUsername(parent.getUsername())
                .setEmail(parent.getEmail())
                .setPhone(parent.getPhone())
                .setPassword(parent.getPassword())
                .setRole(parent.getRole())
                .setFirstName(parent.getFirstName())
                .setLastName(parent.getLastName())
                .build();
    }
}
