package com.mr.daycamps.domain.exception;

public class OverlappingDayCampsException extends RuntimeException {

    private static final String OVERLAPPING_DAY_CAMPS_MESSAGE =
            "The child cannot be enrolled in a day camp that coincides with another day camp for which the child is enrolled.";

    public OverlappingDayCampsException() {
        super(OVERLAPPING_DAY_CAMPS_MESSAGE);
    }

}
