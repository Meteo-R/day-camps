package com.mr.daycamps.api.parent.child.enrollment;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(setterPrefix = "set")
@JsonInclude(JsonInclude.Include.NON_NULL)
class ChildEnrolledInAnyDayCampResponse {

    private final boolean isChildEnrolledInAnyDayCamp;
}
