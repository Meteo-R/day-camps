package com.mr.daycamps.domain.exception;

public class DayCampDatesChangeNotAllowedException extends RuntimeException {

    private static final String DAY_CAMP_DATES_CHANGE_NOT_ALLOWED_MESSAGE =
            "Day camp dates cannot be changed if children are enrolled; day camp id = %d";

    public DayCampDatesChangeNotAllowedException(Long dayCampId) {
        super(String.format(DAY_CAMP_DATES_CHANGE_NOT_ALLOWED_MESSAGE, dayCampId));
    }
}
