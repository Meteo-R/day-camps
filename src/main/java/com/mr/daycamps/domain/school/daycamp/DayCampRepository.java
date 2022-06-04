package com.mr.daycamps.domain.school.daycamp;

import com.mr.daycamps.infrastructure.enrollment.DayCampEntity;

import java.util.List;

public interface DayCampRepository {

    DayCampEntity getDayCamp(Long dayCampId);

    void addChild(Long dayCampId, Long childId);

    void deleteChild(Long dayCampId, Long childId);

    List<DayCampEntity> getAll();
}
