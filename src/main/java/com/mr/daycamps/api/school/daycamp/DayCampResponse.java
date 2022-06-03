package com.mr.daycamps.api.school.daycamp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mr.daycamps.api.parent.child.ChildResponse;
import com.mr.daycamps.api.school.SchoolResponse;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Builder(setterPrefix = "set")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DayCampResponse {

    @NotNull
    private Long id;

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

    private Integer numberOfEnrolled;

    private SchoolResponse school;

    private List<ChildResponse> children;
}
