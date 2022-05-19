package com.mr.daycamps.api.school.daycamp;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
class AddDayCampRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @NotNull
    private BigDecimal price;

    @NotNull
    private Integer capacity;

}
