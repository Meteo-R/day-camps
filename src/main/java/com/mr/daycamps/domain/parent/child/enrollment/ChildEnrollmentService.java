package com.mr.daycamps.domain.parent.child.enrollment;

import com.mr.daycamps.domain.authentication.Parent;

import java.util.List;

public interface ChildEnrollmentService {

    void enrollChild(Long childId, Long dayCampId);

    List<Enrollment> getEnrollments(Parent parent);
}
