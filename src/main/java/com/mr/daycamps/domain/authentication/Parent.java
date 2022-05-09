package com.mr.daycamps.domain.authentication;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(setterPrefix = "set")
public class Parent extends User {
    private String firstName;
    private String lastName;
}
