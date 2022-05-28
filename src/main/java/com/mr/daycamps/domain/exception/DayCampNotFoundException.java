package com.mr.daycamps.domain.exception;

public class DayCampNotFoundException extends RuntimeException {

    private static final String DAY_CAMP_NOT_FOUND_MESSAGE = "Day camp with id %d not found in repository!";

    public DayCampNotFoundException(Long dayCampId) {
        super(String.format(DAY_CAMP_NOT_FOUND_MESSAGE, dayCampId));
    }

}
