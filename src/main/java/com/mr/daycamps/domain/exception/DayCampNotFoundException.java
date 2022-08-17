package com.mr.daycamps.domain.exception;

public class DayCampNotFoundException extends RuntimeException {

    private static final String DAY_CAMP_NOT_FOUND_MESSAGE = "Day camp with id %d not found in repository!";
    private static final String SCHOOLS_DAY_CAMP_NOT_FOUND_MESSAGE = "School with username %s does not offer day camp with id %d";

    public DayCampNotFoundException(String schoolUsername, Long dayCampId) {
        super(String.format(SCHOOLS_DAY_CAMP_NOT_FOUND_MESSAGE, schoolUsername, dayCampId));
    }

    public DayCampNotFoundException(Long dayCampId) {
        super(String.format(DAY_CAMP_NOT_FOUND_MESSAGE, dayCampId));
    }

}
