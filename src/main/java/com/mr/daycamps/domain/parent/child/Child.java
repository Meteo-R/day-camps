package com.mr.daycamps.domain.parent.child;

import com.mr.daycamps.domain.school.daycamp.DayCamp;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder(setterPrefix = "set")
public class Child {
    private Long id;
    private String firstName;
    private String lastName;
    private Set<DayCamp> dayCamps;
}
