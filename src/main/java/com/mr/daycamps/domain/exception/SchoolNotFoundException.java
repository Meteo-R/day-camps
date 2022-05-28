package com.mr.daycamps.domain.exception;

public class SchoolNotFoundException extends RuntimeException {

    private static final String SCHOOL_NOT_FOUND_MESSAGE = "School with username %s not found in repository!";

    public SchoolNotFoundException(String username) {
        super(String.format(SCHOOL_NOT_FOUND_MESSAGE, username));
    }
}
