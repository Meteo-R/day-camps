package com.mr.daycamps.api.school.daycamp;

import com.mr.daycamps.domain.school.daycamp.DayCamp;
import com.mr.daycamps.infrastructure.enrollment.DayCampEntity;
import org.springframework.stereotype.Component;

@Component
class DayCampMapper {

    public DayCamp mapAddDayCampRequest(AddDayCampRequest addDayCampRequest) {
        return DayCamp.builder()
                .setName(addDayCampRequest.getName())
                .setDescription(addDayCampRequest.getDescription())
                .setStartDate(addDayCampRequest.getStartDate())
                .setEndDate(addDayCampRequest.getEndDate())
                .setPrice(addDayCampRequest.getPrice())
                .setCapacity(addDayCampRequest.getCapacity())
                .build();
    }

    public DayCampResponse mapToDayCampResponse(DayCampEntity dayCampEntity) {
        return DayCampResponse.builder()
                .setId(dayCampEntity.getId())
                .setName(dayCampEntity.getName())
                .setDescription(dayCampEntity.getDescription())
                .setStartDate(dayCampEntity.getStartDate())
                .setEndDate(dayCampEntity.getEndDate())
                .setPrice(dayCampEntity.getPrice())
                .setCapacity(dayCampEntity.getCapacity())
                .build();
    }
}
