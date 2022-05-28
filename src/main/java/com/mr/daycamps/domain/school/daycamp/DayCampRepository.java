package com.mr.daycamps.domain.school.daycamp;

public interface DayCampRepository {

    DayCamp getDayCampWithChildren(Long dayCampId);

    void addChild(Long dayCampId, Long childId);

}
