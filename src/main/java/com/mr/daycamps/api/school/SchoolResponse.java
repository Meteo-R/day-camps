package com.mr.daycamps.api.school;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(setterPrefix = "set")
public class SchoolResponse {

    private String name;

    private String address;

    private String email;

    private String phone;

}
