package com.mr.daycamps.api.parent.child.enrollment;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder(setterPrefix = "set")
@JsonInclude(JsonInclude.Include.NON_NULL)
class EnrollmentsResponse {

    private final List<EnrollmentResponse> enrollments;

}
