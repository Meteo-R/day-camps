package com.mr.daycamps.domain.parent.child;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(setterPrefix = "set")
public class Child {
    private Long id;
    private String firstName;
    private String lastName;
}
