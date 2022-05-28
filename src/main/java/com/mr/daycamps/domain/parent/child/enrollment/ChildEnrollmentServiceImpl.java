package com.mr.daycamps.domain.parent.child.enrollment;

import com.mr.daycamps.domain.exception.DayCampCapacityReachedException;
import com.mr.daycamps.domain.exception.DayCampStartDatePassedException;
import com.mr.daycamps.domain.exception.OverlappingDayCampsException;
import com.mr.daycamps.domain.parent.child.Child;
import com.mr.daycamps.domain.parent.child.ChildRepository;
import com.mr.daycamps.domain.school.daycamp.DayCamp;
import com.mr.daycamps.domain.school.daycamp.DayCampRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
class ChildEnrollmentServiceImpl implements ChildEnrollmentService {

    private ChildRepository childRepository;
    private DayCampRepository dayCampRepository;

    @Override
    public void enrollChild(Long childId, Long dayCampId) {
        Child child = childRepository.getChildWithDayCamps(childId);
        DayCamp dayCamp = dayCampRepository.getDayCampWithChildren(dayCampId);

        validateEnrollmentPrerequisites(child, dayCamp);
        dayCampRepository.addChild(dayCampId, childId);
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
