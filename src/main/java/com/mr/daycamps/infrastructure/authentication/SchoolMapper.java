package com.mr.daycamps.infrastructure.authentication;

import com.mr.daycamps.domain.authentication.School;
import com.mr.daycamps.domain.authentication.User;
import com.mr.daycamps.infrastructure.users.SchoolEntity;
import org.springframework.stereotype.Component;

@Component
class SchoolMapper extends UserMapper {

    @Override
    User mapUser(UserEntity userEntity) {
        SchoolEntity schoolEntity = (SchoolEntity) userEntity;
        return School.builder()
                .setUsername(schoolEntity.getUsername())
                .setEmail(schoolEntity.getEmail())
                .setPhone(schoolEntity.getPhone())
                .setPassword(schoolEntity.getPassword())
                .setRole(schoolEntity.getRole())
                .setName(schoolEntity.getName())
                .setAddress(schoolEntity.getAddress())
                .build();
    }

    @Override
    UserEntity mapUser(User user) {
        School school = (School) user;
        return SchoolEntity.builder()
                .setUsername(school.getUsername())
                .setEmail(school.getEmail())
                .setPhone(school.getPhone())
                .setPassword(school.getPassword())
                .setRole(school.getRole())
                .setName(school.getName())
                .setAddress(school.getAddress())
                .build();
    }
}
