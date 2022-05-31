package com.mr.daycamps.api.parent.child;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@Builder(setterPrefix = "set")
public class ChildResponse {

    @NotNull
    private final Long id;

    @NotNull
    private final String firstName;

    @NotNull
    private final String lastName;
}
