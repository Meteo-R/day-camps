package com.mr.daycamps.domain.exception;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DayCampStartDatePassedException extends RuntimeException {

    private static final String DAY_CAMP_START_DATE_PASSED_MESSAGE =
            "Cannot modify enrollment for a day camp, the start date of which (%s) has already passed.";

    public DayCampStartDatePassedException(LocalDate startDate) {
        super(String.format(DAY_CAMP_START_DATE_PASSED_MESSAGE, startDate.format(DateTimeFormatter.ISO_DATE)));
    }

}
