package com.mr.daycamps.domain.exception;

public class ChildNotFoundException extends RuntimeException {

    private static final String CHILD_NOT_FOUND_MESSAGE = "Parent with username %s does not have child with id %d";

    public ChildNotFoundException(String parentUsername, Long childId) {
        super(String.format(CHILD_NOT_FOUND_MESSAGE, parentUsername, childId));
    }
}
