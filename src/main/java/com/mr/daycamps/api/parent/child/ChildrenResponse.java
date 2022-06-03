package com.mr.daycamps.api.parent.child;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder(setterPrefix = "set")
@JsonInclude(JsonInclude.Include.NON_NULL)
class ChildrenResponse {

    private final List<ChildResponse> children;

}
