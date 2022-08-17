package com.mr.daycamps.infrastructure.users;

import com.mr.daycamps.domain.authentication.School;
import com.mr.daycamps.domain.school.daycamp.DayCamp;
import com.mr.daycamps.infrastructure.enrollment.DayCampEntity;

import java.util.Set;

public interface SchoolRepository {
    DayCampEntity addDayCamp(School school, DayCamp dayCamp);

    Set<DayCampEntity> getDayCamps(School school);

    DayCampEntity getDayCamp(School school, Long dayCampId);

    void updateDayCamp(School school, Long dayCampId, DayCamp dayCampUpdateData);
}
