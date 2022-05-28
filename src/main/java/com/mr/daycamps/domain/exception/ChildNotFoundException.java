package com.mr.daycamps.domain.exception;

public class ChildNotFoundException extends RuntimeException {

    private static final String PARENTS_CHILD_NOT_FOUND_MESSAGE = "Parent with username %s does not have child with id %d";
    private static final String CHILD_NOT_FOUND_MESSAGE = "Child with id %d not found in repository!";

    public ChildNotFoundException(String parentUsername, Long childId) {
        super(String.format(PARENTS_CHILD_NOT_FOUND_MESSAGE, parentUsername, childId));
    }

    public ChildNotFoundException(Long childId) {
        super(String.format(CHILD_NOT_FOUND_MESSAGE, childId));
    }
}
