package com.mr.daycamps.infrastructure.authentication;

import com.mr.daycamps.domain.authentication.User;
import com.mr.daycamps.domain.authentication.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;
    private final UserMapper userMapper;

    public UserRepositoryImpl(UserJpaRepository userJpaRepository, UserMapper userMapper) {
        this.userJpaRepository = userJpaRepository;
        this.userMapper = userMapper;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        Optional<UserEntity> userEntityOptional = userJpaRepository.findByUsername(username);
        return userEntityOptional.map(userMapper::mapUser);
    }

    @Override
    public Boolean existsByUsername(String username) {
        return userJpaRepository.existsByUsername(username);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userJpaRepository.existsByEmail(email);
    }

    @Override
    public void save(User user) {
        UserEntity userEntity = userMapper.mapUser(user);
        userJpaRepository.save(userEntity);
    }
}
