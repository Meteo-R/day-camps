package com.mr.daycamps.domain.parent.child;

import com.mr.daycamps.infrastructure.enrollment.ChildEntity;

public interface ChildRepository {

    ChildEntity getChild(Long childId);

}
