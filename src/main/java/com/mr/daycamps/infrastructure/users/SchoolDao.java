package com.mr.daycamps.infrastructure.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SchoolDao extends JpaRepository<SchoolEntity, Long> {

    Optional<SchoolEntity> findByUsername(String username);

}
