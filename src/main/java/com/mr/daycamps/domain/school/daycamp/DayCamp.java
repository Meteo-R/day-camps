package com.mr.daycamps.domain.school.daycamp;

import com.mr.daycamps.domain.parent.child.Child;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Builder(setterPrefix = "set")
public class DayCamp {

    private Long id;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal price;
    private Integer capacity;
    private Set<Child> children;

}
