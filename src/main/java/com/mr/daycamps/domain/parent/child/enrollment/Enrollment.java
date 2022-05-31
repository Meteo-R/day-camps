package com.mr.daycamps.domain.parent.child.enrollment;

import com.mr.daycamps.infrastructure.enrollment.ChildEntity;
import com.mr.daycamps.infrastructure.enrollment.DayCampEntity;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder(setterPrefix = "set")

public class Enrollment {

    private ChildEntity child;
    private Set<DayCampEntity> dayCamps;

}
