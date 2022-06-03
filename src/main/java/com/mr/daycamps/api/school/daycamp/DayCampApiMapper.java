package com.mr.daycamps.api.school.daycamp;

import com.mr.daycamps.api.commons.TimelineLocationFilter;
import com.mr.daycamps.api.parent.ParentResponse;
import com.mr.daycamps.api.parent.child.ChildResponse;
import com.mr.daycamps.domain.school.daycamp.DayCamp;
import com.mr.daycamps.infrastructure.enrollment.DayCampEntity;
import com.mr.daycamps.infrastructure.enrollment.TimelineLocation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
class DayCampApiMapper {

    private final TimelineLocationFilter timelineLocationFilter;

    public DayCamp mapAddDayCampRequest(AddDayCampRequest addDayCampRequest) {
        return DayCamp.builder()
                .setName(addDayCampRequest.getName())
                .setDescription(addDayCampRequest.getDescription())
                .setStartDate(addDayCampRequest.getStartDate())
                .setEndDate(addDayCampRequest.getEndDate())
                .setPrice(addDayCampRequest.getPrice())
                .setCapacity(addDayCampRequest.getCapacity())
                .build();
    }

    public DayCampResponse mapToAddedDayCampResponse(DayCampEntity dayCampEntity) {
        return DayCampResponse.builder()
                .setId(dayCampEntity.getId())
                .setName(dayCampEntity.getName())
                .setDescription(dayCampEntity.getDescription())
                .setStartDate(dayCampEntity.getStartDate())
                .setEndDate(dayCampEntity.getEndDate())
                .setPrice(dayCampEntity.getPrice())
                .setCapacity(dayCampEntity.getCapacity())
                .build();
    }

    public DayCampsResponse mapToDayCampsResponse(Set<DayCampEntity> dayCamps, List<TimelineLocation> timelineLocation) {
        return DayCampsResponse.builder()
                .setDayCamps(dayCamps.stream()
                        .map(dayCamp -> DayCampResponse.builder()
                                .setId(dayCamp.getId())
                                .setName(dayCamp.getName())
                                .setDescription(dayCamp.getDescription())
                                .setStartDate(dayCamp.getStartDate())
                                .setEndDate(dayCamp.getEndDate())
                                .setPrice(dayCamp.getPrice())
                                .setCapacity(dayCamp.getCapacity())
                                .setNumberOfEnrolled(dayCamp.getChildren().size())
                                .setChildren(dayCamp.getChildren().stream()
                                        .map(child -> ChildResponse.builder()
                                                .setFirstName(child.getFirstName())
                                                .setLastName(child.getLastName())
                                                .setParent(ParentResponse.builder()
                                                        .setFirstName(child.getParent().getFirstName())
                                                        .setLastName(child.getParent().getLastName())
                                                        .setEmail(child.getParent().getEmail())
                                                        .setPhone(child.getParent().getPhone())
                                                        .build())
                                                .build())
                                        .sorted(Comparator.comparing(ChildResponse::getLastName).thenComparing(ChildResponse::getFirstName))
                                        .collect(Collectors.toList())
                                )
                                .build())
                        .filter(dayCamp -> timelineLocationFilter.doFilter(dayCamp.getStartDate(), dayCamp.getEndDate(), timelineLocation))
                        .sorted(Comparator.comparing(DayCampResponse::getStartDate))
                        .collect(Collectors.toList())
                )
                .build();
    }
}
