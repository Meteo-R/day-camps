package com.mr.daycamps.api.school;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(setterPrefix = "set")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SchoolResponse {

    private String name;

    private String address;

    private String email;

    private String phone;

}
