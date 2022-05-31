package com.mr.daycamps.api.commons;

import com.mr.daycamps.infrastructure.enrollment.TimelineLocation;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.List;

@Component
public class TimelineLocationFilter {

    public boolean doFilter(LocalDate startDate, LocalDate endDate, List<TimelineLocation> timelineLocation) {
        if (CollectionUtils.isEmpty(timelineLocation)) {
            return true;
        }

        LocalDate today = LocalDate.now();
        return (timelineLocation.contains(TimelineLocation.PAST) && today.isAfter(endDate))
                || (timelineLocation.contains(TimelineLocation.ONGOING) && !today.isBefore(startDate) && !today.isAfter(endDate))
                || (timelineLocation.contains(TimelineLocation.FUTURE) && today.isBefore(startDate));
    }

}
