package com.mr.daycamps.domain.parent.child.enrollment;

import com.mr.daycamps.domain.authentication.Parent;
import com.mr.daycamps.infrastructure.enrollment.DayCampEntity;

import java.util.List;

public interface ChildEnrollmentService {

    void validateChildAgainstParent(Long childId, Parent parent);

    void enrollChild(Long childId, Long dayCampId);

    List<Enrollment> getEnrollments(Parent parent);

    void unenrollChild(Long childId, Long dayCampId);

    List<DayCampEntity> getPossibleDayCampsForChild(Long childId);
}
