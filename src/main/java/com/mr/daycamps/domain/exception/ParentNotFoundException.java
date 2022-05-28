package com.mr.daycamps.domain.exception;

public class ParentNotFoundException extends RuntimeException {

    private static final String PARENT_NOT_FOUND_MESSAGE = "Parent with username %s not found in repository!";

    public ParentNotFoundException(String username) {
        super(String.format(PARENT_NOT_FOUND_MESSAGE, username));
    }
}
