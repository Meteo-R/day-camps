package com.mr.daycamps.api.parent.child;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder(setterPrefix = "set")
class ChildrenResponse {

    private final List<ChildResponse> children;

}
