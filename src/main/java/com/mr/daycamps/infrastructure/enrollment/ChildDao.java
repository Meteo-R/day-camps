package com.mr.daycamps.infrastructure.enrollment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChildDao extends JpaRepository<ChildEntity, Long> {
}
