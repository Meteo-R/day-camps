package com.mr.daycamps.domain.exception;

public class DayCampCapacityLoweredBelowTotalEnrollmentException extends RuntimeException {

    private static final String DAY_CAMP_CAPACITY_LOWERED_BELOW_TOTAL_ENROLLMENT_MESSAGE =
            "Day camp capacity cannot be lowered below the current total number of enrolled children; day camp id = %d";

    public DayCampCapacityLoweredBelowTotalEnrollmentException(Long dayCampId) {
        super(String.format(DAY_CAMP_CAPACITY_LOWERED_BELOW_TOTAL_ENROLLMENT_MESSAGE, dayCampId));
    }
}
