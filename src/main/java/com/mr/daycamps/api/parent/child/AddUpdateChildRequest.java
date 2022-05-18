package com.mr.daycamps.api.parent.child;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
class AddUpdateChildRequest {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;
}
