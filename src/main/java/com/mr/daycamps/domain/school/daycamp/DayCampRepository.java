package com.mr.daycamps.domain.school.daycamp;

import com.mr.daycamps.infrastructure.enrollment.DayCampEntity;

import java.util.List;

public interface DayCampRepository {

    DayCamp getDayCampWithChildren(Long dayCampId);

    void addChild(Long dayCampId, Long childId);

    void deleteChild(Long dayCampId, Long childId);

    List<DayCampEntity> getAll();
}
