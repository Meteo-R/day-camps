package com.mr.daycamps.domain.parent.child.enrollment;

import com.mr.daycamps.domain.authentication.Parent;
import com.mr.daycamps.domain.exception.ChildAlreadyEnrolledException;
import com.mr.daycamps.domain.exception.ChildNotEnrolledException;
import com.mr.daycamps.domain.exception.ChildNotFoundException;
import com.mr.daycamps.domain.exception.DayCampCapacityReachedException;
import com.mr.daycamps.domain.exception.DayCampStartDatePassedException;
import com.mr.daycamps.domain.exception.OverlappingDayCampsException;
import com.mr.daycamps.domain.parent.child.ChildRepository;
import com.mr.daycamps.domain.school.daycamp.DayCampRepository;
import com.mr.daycamps.infrastructure.enrollment.ChildEntity;
import com.mr.daycamps.infrastructure.enrollment.DayCampEntity;
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
    public void validateChildAgainstParent(Long childId, Parent parent) {
        parentRepository.getChildren(parent).stream()
                .filter(child -> child.getId().equals(childId))
                .findAny()
                .orElseThrow(() -> new ChildNotFoundException(parent.getUsername(), childId));
    }

    @Override
    public void enrollChild(Long childId, Long dayCampId) {
        ChildEntity child = childRepository.getChild(childId);
        DayCampEntity dayCamp = dayCampRepository.getDayCamp(dayCampId);

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
        ChildEntity child = childRepository.getChild(childId);
        DayCampEntity dayCamp = dayCampRepository.getDayCamp(dayCampId);

        validateUnenrollmentPrerequisites(child, dayCamp);
        dayCampRepository.deleteChild(dayCampId, childId);
    }

    @Override
    public List<DayCampEntity> getPossibleDayCampsForChild(Long childId) {
        ChildEntity child = childRepository.getChild(childId);
        return dayCampRepository.getAll().stream()
                .filter(dayCamp -> dayCamp.getStartDate().isAfter(LocalDate.now()))
                .filter(dayCamp -> dayCamp.getChildren().size() < dayCamp.getCapacity())
                .filter(dayCamp -> !isDayCampOverlappingWithAnyOtherAttendedCamp(dayCamp, child))
                .collect(Collectors.toList());
    }

    private void validateEnrollmentPrerequisites(ChildEntity child, DayCampEntity dayCamp) {
        validateChildIsNotYetEnrolledInThisDayCamp(child, dayCamp);
        validateDayCampHasNotStartedYet(dayCamp);
        validateDayCampCanAcceptAnotherChild(dayCamp);
        validateChildHasNoOtherDayCampsAtThatTime(child, dayCamp);
    }

    private void validateChildIsNotYetEnrolledInThisDayCamp(ChildEntity child, DayCampEntity dayCamp) {
        if (isAlreadyEnrolled(child, dayCamp)) {
            throw new ChildAlreadyEnrolledException(child.getId(), dayCamp.getId());
        }
    }

    private void validateDayCampCanAcceptAnotherChild(DayCampEntity dayCamp) {
        if (dayCamp.getChildren().size() >= dayCamp.getCapacity()) {
            throw new DayCampCapacityReachedException(dayCamp.getCapacity());
        }
    }

    private void validateChildHasNoOtherDayCampsAtThatTime(ChildEntity child, DayCampEntity dayCamp) {
        if (isDayCampOverlappingWithAnyOtherAttendedCamp(dayCamp, child)) {
            throw new OverlappingDayCampsException();
        }
    }

    private boolean isDayCampOverlappingWithAnyOtherAttendedCamp(DayCampEntity dayCamp, ChildEntity child) {
        return child.getDayCamps().stream()
                .anyMatch(attendedCamp -> !dayCamp.getStartDate().isAfter(attendedCamp.getEndDate())
                        && !dayCamp.getEndDate().isBefore(attendedCamp.getStartDate()));
    }

    private void validateUnenrollmentPrerequisites(ChildEntity child, DayCampEntity dayCamp) {
        validateChildIsCurrentlyEnrolled(child, dayCamp);
        validateDayCampHasNotStartedYet(dayCamp);
    }

    private void validateChildIsCurrentlyEnrolled(ChildEntity child, DayCampEntity dayCamp) {
        if (isNotEnrolled(child, dayCamp)) {
            throw new ChildNotEnrolledException(child.getId(), dayCamp.getId());
        }
    }

    private boolean isNotEnrolled(ChildEntity child, DayCampEntity dayCamp) {
        return !isAlreadyEnrolled(child, dayCamp);
    }

    private boolean isAlreadyEnrolled(ChildEntity child, DayCampEntity dayCamp) {
        return child.getDayCamps().stream()
                .map(DayCampEntity::getId)
                .collect(Collectors.toList())
                .contains(dayCamp.getId());
    }

    private void validateDayCampHasNotStartedYet(DayCampEntity dayCamp) {
        LocalDate today = LocalDate.now();
        if (!today.isBefore(dayCamp.getStartDate())) {
            throw new DayCampStartDatePassedException(dayCamp.getStartDate());
        }
    }

}
