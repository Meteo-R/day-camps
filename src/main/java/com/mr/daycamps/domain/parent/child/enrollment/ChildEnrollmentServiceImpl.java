package com.mr.daycamps.domain.parent.child.enrollment;

import com.mr.daycamps.domain.authentication.Parent;
import com.mr.daycamps.domain.exception.DayCampCapacityReachedException;
import com.mr.daycamps.domain.exception.DayCampStartDatePassedException;
import com.mr.daycamps.domain.exception.OverlappingDayCampsException;
import com.mr.daycamps.domain.parent.child.Child;
import com.mr.daycamps.domain.parent.child.ChildRepository;
import com.mr.daycamps.domain.school.daycamp.DayCamp;
import com.mr.daycamps.domain.school.daycamp.DayCampRepository;
import com.mr.daycamps.infrastructure.enrollment.ChildEntity;
import com.mr.daycamps.infrastructure.users.ParentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class ChildEnrollmentServiceImpl implements ChildEnrollmentService {

    private ChildRepository childRepository;
    private DayCampRepository dayCampRepository;
    private ParentRepository parentRepository;

    @Override
    public void enrollChild(Long childId, Long dayCampId) {
        Child child = childRepository.getChildWithDayCamps(childId);
        DayCamp dayCamp = dayCampRepository.getDayCampWithChildren(dayCampId);

        validateEnrollmentPrerequisites(child, dayCamp);
        dayCampRepository.addChild(dayCampId, childId);
    }

    @Override
    public List<Enrollment> getEnrollments(Parent parent) {
        List<ChildEntity> children = parentRepository.getChildren(parent);

        return children.stream()
                .map(child -> Enrollment.builder()
                        .setChild(child)
                        .setDayCamps(child.getDayCamps())
                        .build())
                .collect(Collectors.toList());
    }

    private void validateEnrollmentPrerequisites(Child child, DayCamp dayCamp) {
        validateDayCampStartDateIsAfterToday(dayCamp);
        validateDayCampCanAcceptAnotherChild(dayCamp);
        validateChildHasNoOtherDayCampsAtThatTime(child, dayCamp);
    }

    private void validateDayCampStartDateIsAfterToday(DayCamp dayCamp) {
        if (!LocalDate.now().isBefore(dayCamp.getStartDate())) {
            throw new DayCampStartDatePassedException(dayCamp.getStartDate());
        }
    }

    private void validateDayCampCanAcceptAnotherChild(DayCamp dayCamp) {
        if (dayCamp.getChildren().size() >= dayCamp.getCapacity()) {
            throw new DayCampCapacityReachedException(dayCamp.getCapacity());
        }
    }

    private void validateChildHasNoOtherDayCampsAtThatTime(Child child, DayCamp dayCamp) {
        if (isDayCampOverlappingWithAnyOtherAttendedCamp(dayCamp, child)) {
            throw new OverlappingDayCampsException();
        }
    }

    private boolean isDayCampOverlappingWithAnyOtherAttendedCamp(DayCamp dayCamp, Child child) {
        return child.getDayCamps().stream()
                .anyMatch(attendedCamp -> !dayCamp.getStartDate().isAfter(attendedCamp.getEndDate())
                        && !dayCamp.getEndDate().isBefore(attendedCamp.getStartDate()));
    }

}
