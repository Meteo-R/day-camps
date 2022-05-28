package com.mr.daycamps.infrastructure.enrollment;

import com.mr.daycamps.domain.school.daycamp.DayCamp;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
class DayCampMapper {

    public Set<DayCamp> mapDayCamps(Set<DayCampEntity> dayCamps) {
        return dayCamps.stream()
                .map(this::mapDayCamp)
                .collect(Collectors.toUnmodifiableSet());
    }

    private DayCamp mapDayCamp(DayCampEntity dayCampEntity) {
        return DayCamp.builder()
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
