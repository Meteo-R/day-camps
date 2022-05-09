package com.mr.daycamps.infrastructure.authentication;

import com.mr.daycamps.domain.authentication.User;
import com.mr.daycamps.domain.authentication.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;
    private final UserMapperFactory userMapperFactory;

    public UserRepositoryImpl(UserJpaRepository userJpaRepository, UserMapperFactory userMapperFactory) {
        this.userJpaRepository = userJpaRepository;
        this.userMapperFactory = userMapperFactory;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        Optional<UserEntity> userEntityOptional = userJpaRepository.findByUsername(username);
        return userEntityOptional.map(userEntity -> userMapperFactory.getMapper(userEntity).mapUser(userEntity));
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
        UserEntity userEntity = userMapperFactory.getMapper(user).mapUser(user);
        userJpaRepository.save(userEntity);
    }
}
