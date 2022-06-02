package com.mr.daycamps.domain.exception;

public class ChildAlreadyEnrolledException extends RuntimeException {

    private static final String CHILD_ALREADY_ENROLLED_MESSAGE = "Child with id %d is already enrolled in day camp with id %d";

    public ChildAlreadyEnrolledException(Long childId, Long dayCampId) {
        super(String.format(CHILD_ALREADY_ENROLLED_MESSAGE, childId, dayCampId));
    }

}
