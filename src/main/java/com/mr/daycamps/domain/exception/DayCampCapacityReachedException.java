package com.mr.daycamps.domain.exception;

public class DayCampCapacityReachedException extends RuntimeException {

    private static final String DAY_CAMP_CAPACITY_REACHED_MESSAGE = "The capacity of the day camp (%d) has been reached!";

    public DayCampCapacityReachedException(Integer capacity) {
        super(String.format(DAY_CAMP_CAPACITY_REACHED_MESSAGE, capacity));
    }

}
