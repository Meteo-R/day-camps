package com.mr.daycamps.domain.authentication;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(setterPrefix = "set")
public class School extends User {
    private String name;
    private String address;
}
