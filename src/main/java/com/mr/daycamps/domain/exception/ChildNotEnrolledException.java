package com.mr.daycamps.domain.exception;

public class ChildNotEnrolledException extends RuntimeException {

    private static final String CHILD_NOT_ENROLLED_MESSAGE = "Child with id %d is not enrolled in day camp with id %d";

    public ChildNotEnrolledException(Long childId, Long dayCampId) {
        super(String.format(CHILD_NOT_ENROLLED_MESSAGE, childId, dayCampId));
    }
}
