package com.mr.daycamps.api.parent;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(setterPrefix = "set")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ParentResponse {

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

}
