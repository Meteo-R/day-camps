package com.mr.daycamps.domain.authentication;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    void save(User user);
}
