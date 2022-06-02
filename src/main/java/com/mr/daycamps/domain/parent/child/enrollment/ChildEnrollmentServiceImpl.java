package com.mr.daycamps.domain.parent.child.enrollment;

import com.mr.daycamps.domain.authentication.Parent;
import com.mr.daycamps.domain.exception.ChildAlreadyEnrolledException;
import com.mr.daycamps.domain.exception.ChildNotEnrolledException;
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

    @Override
    public void unenrollChild(Long childId, Long dayCampId) {
        Child child = childRepository.getChildWithDayCamps(childId);
        DayCamp dayCamp = dayCampRepository.getDayCampWithChildren(dayCampId);

        validateUnenrollmentPrerequisites(child, dayCamp);
        dayCampRepository.deleteChild(dayCampId, childId);
    }

    private void validateEnrollmentPrerequisites(Child child, DayCamp dayCamp) {
        validateChildIsNotYetEnrolledInThisDayCamp(child, dayCamp);
        validateDayCampHasNotStartedYet(dayCamp);
        validateDayCampCanAcceptAnotherChild(dayCamp);
        validateChildHasNoOtherDayCampsAtThatTime(child, dayCamp);
    }

    private void validateChildIsNotYetEnrolledInThisDayCamp(Child child, DayCamp dayCamp) {
        if (isAlreadyEnrolled(child, dayCamp)) {
            throw new ChildAlreadyEnrolledException(child.getId(), dayCamp.getId());
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

    private void validateUnenrollmentPrerequisites(Child child, DayCamp dayCamp) {
        validateChildIsCurrentlyEnrolled(child, dayCamp);
        validateDayCampHasNotStartedYet(dayCamp);
    }

    private void validateChildIsCurrentlyEnrolled(Child child, DayCamp dayCamp) {
        if (isNotEnrolled(child, dayCamp)) {
            throw new ChildNotEnrolledException(child.getId(), dayCamp.getId());
        }
    }

    private boolean isNotEnrolled(Child child, DayCamp dayCamp) {
        return !isAlreadyEnrolled(child, dayCamp);
    }

    private boolean isAlreadyEnrolled(Child child, DayCamp dayCamp) {
        return child.getDayCamps().stream()
                .map(DayCamp::getId)
                .collect(Collectors.toList())
                .contains(dayCamp.getId());
    }

    private void validateDayCampHasNotStartedYet(DayCamp dayCamp) {
        LocalDate today = LocalDate.now();
        if (!today.isBefore(dayCamp.getStartDate())) {
            throw new DayCampStartDatePassedException(dayCamp.getStartDate());
        }
    }

}
