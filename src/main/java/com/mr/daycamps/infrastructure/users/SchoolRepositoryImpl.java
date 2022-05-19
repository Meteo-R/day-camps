package com.mr.daycamps.infrastructure.users;

import com.mr.daycamps.domain.authentication.School;
import com.mr.daycamps.domain.school.daycamp.DayCamp;
import com.mr.daycamps.infrastructure.enrollment.DayCampDao;
import com.mr.daycamps.infrastructure.enrollment.DayCampEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class SchoolRepositoryImpl implements SchoolRepository {

    private static final String SCHOOL_NOT_FOUND_MESSAGE = "School with username %s not found in repository!";

    private final SchoolDao schoolDao;
    private final DayCampDao dayCampDao;

    public SchoolRepositoryImpl(SchoolDao schoolDao, DayCampDao dayCampDao) {
        this.schoolDao = schoolDao;
        this.dayCampDao = dayCampDao;
    }

    @Override
    public DayCampEntity addDayCamp(School school, DayCamp dayCamp) {
        Optional<SchoolEntity> schoolEntityOptional = schoolDao.findByUsername(school.getUsername());
        return schoolEntityOptional.map(schoolEntity -> saveDayCamp(dayCamp, schoolEntity))
                .orElseThrow(() -> new IllegalStateException(String.format(SCHOOL_NOT_FOUND_MESSAGE, school.getUsername())));
    }

    private DayCampEntity saveDayCamp(DayCamp dayCamp, SchoolEntity schoolEntity) {
        DayCampEntity dayCampEntity = DayCampEntity.builder()
                .setName(dayCamp.getName())
                .setDescription(dayCamp.getDescription())
                .setStartDate(dayCamp.getStartDate())
                .setEndDate(dayCamp.getEndDate())
                .setPrice(dayCamp.getPrice())
                .setCapacity(dayCamp.getCapacity())
                .setSchool(schoolEntity)
                .build();
        schoolEntity.getDayCamps().add(dayCampEntity);
        return dayCampDao.save(dayCampEntity);
    }
}
