package com.mr.daycamps.api.parent.child.enrollment;

import com.mr.daycamps.api.parent.child.ChildResponse;
import com.mr.daycamps.api.school.daycamp.DayCampResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder(setterPrefix = "set")
class EnrollmentResponse {

    private final ChildResponse child;
    private final List<DayCampResponse> dayCamps;

}
