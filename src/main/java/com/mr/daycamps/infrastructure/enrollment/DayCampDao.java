package com.mr.daycamps.infrastructure.enrollment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DayCampDao extends JpaRepository<DayCampEntity, Long> {
}
