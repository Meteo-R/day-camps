package com.mr.daycamps.infrastructure.users;

import com.mr.daycamps.domain.authentication.School;
import com.mr.daycamps.domain.school.daycamp.DayCamp;
import com.mr.daycamps.infrastructure.enrollment.DayCampEntity;

public interface SchoolRepository {
    DayCampEntity addDayCamp(School school, DayCamp dayCamp);
}