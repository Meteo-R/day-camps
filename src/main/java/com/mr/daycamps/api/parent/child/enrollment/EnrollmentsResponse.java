package com.mr.daycamps.api.parent.child.enrollment;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder(setterPrefix = "set")
class EnrollmentsResponse {

    private final List<EnrollmentResponse> enrollments;

}
