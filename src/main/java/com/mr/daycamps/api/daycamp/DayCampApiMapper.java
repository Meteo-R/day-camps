package com.mr.daycamps.api.daycamp;

import com.mr.daycamps.api.commons.TimelineLocationFilter;
import com.mr.daycamps.api.parent.ParentResponse;
import com.mr.daycamps.api.parent.child.ChildResponse;
import com.mr.daycamps.api.school.SchoolResponse;
import com.mr.daycamps.api.school.daycamp.AddUpdateDayCampRequest;
import com.mr.daycamps.api.school.daycamp.DayCampResponse;
import com.mr.daycamps.api.school.daycamp.DayCampsResponse;
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
public class DayCampApiMapper {

    private final TimelineLocationFilter timelineLocationFilter;

    public DayCamp mapAddDayCampRequest(AddUpdateDayCampRequest addUpdateDayCampRequest) {
        return DayCamp.builder()
                .setName(addUpdateDayCampRequest.getName())
                .setDescription(addUpdateDayCampRequest.getDescription())
                .setStartDate(addUpdateDayCampRequest.getStartDate())
                .setEndDate(addUpdateDayCampRequest.getEndDate())
                .setPrice(addUpdateDayCampRequest.getPrice())
                .setCapacity(addUpdateDayCampRequest.getCapacity())
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

    public DayCampsResponse mapToSchoolDayCampsResponse(Set<DayCampEntity> dayCamps, List<TimelineLocation> timelineLocation) {
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

    public DayCampsResponse mapToAllDayCampsResponse(List<DayCampEntity> dayCamps, List<TimelineLocation> timelineLocation) {
        return DayCampsResponse.builder()
                .setDayCamps(dayCamps.stream()
                        .map(this::buildDayCampResponse)
                        .filter(dayCamp -> timelineLocationFilter.doFilter(dayCamp.getStartDate(), dayCamp.getEndDate(), timelineLocation))
                        .sorted(Comparator.comparing(DayCampResponse::getStartDate))
                        .collect(Collectors.toList())
                )
                .build();
    }

    public DayCampResponse buildDayCampResponse(DayCampEntity dayCamp) {
        return DayCampResponse.builder()
                .setId(dayCamp.getId())
                .setName(dayCamp.getName())
                .setDescription(dayCamp.getDescription())
                .setStartDate(dayCamp.getStartDate())
                .setEndDate(dayCamp.getEndDate())
                .setPrice(dayCamp.getPrice())
                .setCapacity(dayCamp.getCapacity())
                .setNumberOfEnrolled(dayCamp.getChildren().size())
                .setSchool(SchoolResponse.builder()
                        .setName(dayCamp.getSchool().getName())
                        .setAddress(dayCamp.getSchool().getAddress())
                        .setEmail(dayCamp.getSchool().getEmail())
                        .setPhone(dayCamp.getSchool().getPhone())
                        .build())
                .build();
    }
}
