package com.mr.daycamps.infrastructure.users;

import com.mr.daycamps.domain.authentication.School;
import com.mr.daycamps.domain.exception.DayCampCapacityLoweredBelowTotalEnrollmentException;
import com.mr.daycamps.domain.exception.DayCampDatesChangeNotAllowedException;
import com.mr.daycamps.domain.exception.DayCampNotFoundException;
import com.mr.daycamps.domain.exception.SchoolNotFoundException;
import com.mr.daycamps.domain.school.daycamp.DayCamp;
import com.mr.daycamps.infrastructure.enrollment.DayCampDao;
import com.mr.daycamps.infrastructure.enrollment.DayCampEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Component
class SchoolRepositoryImpl implements SchoolRepository {

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
                .orElseThrow(() -> new SchoolNotFoundException(school.getUsername()));
    }

    @Override
    public Set<DayCampEntity> getDayCamps(School school) {
        Optional<SchoolEntity> schoolEntityOptional = schoolDao.findByUsername(school.getUsername());
        return schoolEntityOptional.map(SchoolEntity::getDayCamps)
                .orElseThrow(() -> new SchoolNotFoundException(school.getUsername()));
    }

    @Override
    public void updateDayCamp(School school, Long dayCampId, DayCamp dayCampUpdateData) {
        Optional<SchoolEntity> schoolEntityOptional = schoolDao.findByUsername(school.getUsername());
        List<DayCampEntity> dayCampEntities = schoolEntityOptional.map(schoolEntity -> new ArrayList<>(schoolEntity.getDayCamps()))
                .orElseThrow(() -> new SchoolNotFoundException(school.getUsername()));

        DayCampEntity foundDayCamp = dayCampEntities.stream()
                .filter(dayCampEntity -> Objects.equals(dayCampEntity.getId(), dayCampId))
                .findAny()
                .orElseThrow(() -> new DayCampNotFoundException(school.getUsername(), dayCampId));

        validateDayCampDatesChange(foundDayCamp, dayCampUpdateData);
        validateCapacityChange(foundDayCamp, dayCampUpdateData);

        foundDayCamp.setName(dayCampUpdateData.getName());
        foundDayCamp.setDescription(dayCampUpdateData.getDescription());
        foundDayCamp.setStartDate(dayCampUpdateData.getStartDate());
        foundDayCamp.setEndDate(dayCampUpdateData.getEndDate());
        foundDayCamp.setPrice(dayCampUpdateData.getPrice());
        foundDayCamp.setCapacity(dayCampUpdateData.getCapacity());

        dayCampDao.save(foundDayCamp);
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

    private void validateDayCampDatesChange(DayCampEntity foundDayCamp, DayCamp dayCampUpdateData) {
        if (isAttemptToChangeDates(foundDayCamp, dayCampUpdateData) && isAnyChildEnrolled(foundDayCamp)) {
            throw new DayCampDatesChangeNotAllowedException(foundDayCamp.getId());
        }
    }

    private boolean isAttemptToChangeDates(DayCampEntity foundDayCamp, DayCamp dayCampUpdateData) {
        return !Objects.equals(foundDayCamp.getStartDate(), dayCampUpdateData.getStartDate())
                || !Objects.equals(foundDayCamp.getEndDate(), dayCampUpdateData.getEndDate());
    }

    private boolean isAnyChildEnrolled(DayCampEntity foundDayCamp) {
        return foundDayCamp.getChildren().size() > 0;
    }

    private void validateCapacityChange(DayCampEntity foundDayCamp, DayCamp dayCampUpdateData) {
        if (isAttemptToChangeCapacity(foundDayCamp, dayCampUpdateData)
                && isNewCapacityLowerThanCurrentTotalEnrollment(foundDayCamp, dayCampUpdateData)) {
            throw new DayCampCapacityLoweredBelowTotalEnrollmentException(foundDayCamp.getId());
        }
    }

    private boolean isAttemptToChangeCapacity(DayCampEntity foundDayCamp, DayCamp dayCampUpdateData) {
        return !Objects.equals(foundDayCamp.getCapacity(), dayCampUpdateData.getCapacity());
    }

    private boolean isNewCapacityLowerThanCurrentTotalEnrollment(DayCampEntity foundDayCamp, DayCamp dayCampUpdateData) {
        Integer newCapacity = dayCampUpdateData.getCapacity();
        int currentTotalEnrollment = foundDayCamp.getChildren().size();
        return newCapacity < currentTotalEnrollment;
    }
}
