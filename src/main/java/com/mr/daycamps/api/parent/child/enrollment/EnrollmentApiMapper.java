package com.mr.daycamps.api.parent.child.enrollment;

import com.mr.daycamps.api.commons.TimelineLocationFilter;
import com.mr.daycamps.api.parent.child.ChildResponse;
import com.mr.daycamps.api.school.SchoolResponse;
import com.mr.daycamps.api.school.daycamp.DayCampResponse;
import com.mr.daycamps.domain.parent.child.enrollment.Enrollment;
import com.mr.daycamps.infrastructure.enrollment.TimelineLocation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
class EnrollmentApiMapper {

    private final TimelineLocationFilter timelineLocationFilter;

    public EnrollmentsResponse mapToEnrollmentsResponse(List<Enrollment> enrollments, List<TimelineLocation> timelineLocation) {
        return EnrollmentsResponse.builder()
                .setEnrollments(enrollments.stream()
                        .map(enrollment -> EnrollmentResponse.builder()
                                .setChild(ChildResponse.builder()
                                        .setId(enrollment.getChild().getId())
                                        .setFirstName(enrollment.getChild().getFirstName())
                                        .setLastName(enrollment.getChild().getLastName())
                                        .build())
                                .setDayCamps(enrollment.getChild().getDayCamps().stream()
                                        .map(dayCamp -> DayCampResponse.builder()
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
                                                .build())
                                        .filter(dayCamp -> timelineLocationFilter.doFilter(dayCamp.getStartDate(), dayCamp.getEndDate(), timelineLocation))
                                        .sorted(Comparator.comparing(DayCampResponse::getStartDate))
                                        .collect(Collectors.toList())
                                )
                                .build())
                        .sorted(Comparator.comparingLong(enrollment -> enrollment.getChild().getId()))
                        .collect(Collectors.toList())
                )
                .build();
    }
}
