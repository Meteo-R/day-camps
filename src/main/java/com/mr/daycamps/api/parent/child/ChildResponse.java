package com.mr.daycamps.api.parent.child;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mr.daycamps.api.parent.ParentResponse;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@Builder(setterPrefix = "set")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChildResponse {

    private final Long id;

    @NotNull
    private final String firstName;

    @NotNull
    private final String lastName;

    private ParentResponse parent;
}
