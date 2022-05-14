package com.mr.daycamps.infrastructure.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParentJpaRepository extends JpaRepository<ParentEntity, Long> {

    Optional<ParentEntity> findByUsername(String username);

}
