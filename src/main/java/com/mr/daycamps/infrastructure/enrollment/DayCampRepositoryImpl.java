package com.mr.daycamps.infrastructure.enrollment;

import com.mr.daycamps.domain.exception.ChildNotFoundException;
import com.mr.daycamps.domain.exception.DayCampNotFoundException;
import com.mr.daycamps.domain.school.daycamp.DayCamp;
import com.mr.daycamps.domain.school.daycamp.DayCampRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class DayCampRepositoryImpl implements DayCampRepository {

    private DayCampDao dayCampDao;
    private ChildDao childDao;
    private ChildMapper childMapper;

    public DayCamp getDayCampWithChildren(Long dayCampId) {
        return dayCampDao.findById(dayCampId)
                .map(dayCampEntity -> DayCamp.builder()
                        .setId(dayCampEntity.getId())
                        .setName(dayCampEntity.getName())
                        .setDescription(dayCampEntity.getDescription())
                        .setStartDate(dayCampEntity.getStartDate())
                        .setEndDate(dayCampEntity.getEndDate())
                        .setPrice(dayCampEntity.getPrice())
                        .setCapacity(dayCampEntity.getCapacity())
                        .setChildren(childMapper.mapChildren(dayCampEntity.getChildren()))
                        .build())
                .orElseThrow(() -> new DayCampNotFoundException(dayCampId));
    }

    @Override
    public void addChild(Long dayCampId, Long childId) {
        ChildEntity childEntity = childDao.findById(childId).orElseThrow(() -> new ChildNotFoundException(childId));
        DayCampEntity dayCampEntity = dayCampDao.findById(dayCampId).orElseThrow(() -> new DayCampNotFoundException(dayCampId));

        dayCampEntity.getChildren().add(childEntity);
        childEntity.getDayCamps().add(dayCampEntity);

        dayCampDao.save(dayCampEntity);
    }

    @Override
    public void deleteChild(Long dayCampId, Long childId) {
        ChildEntity childEntity = childDao.findById(childId).orElseThrow(() -> new ChildNotFoundException(childId));
        DayCampEntity dayCampEntity = dayCampDao.findById(dayCampId).orElseThrow(() -> new DayCampNotFoundException(dayCampId));

//        dayCampEntity.getChildren().remove(childEntity);
        dayCampEntity.setChildren(dayCampEntity.getChildren().stream().filter(child -> !child.getId().equals(childEntity.getId())).collect(Collectors.toSet()));
//        childEntity.getDayCamps().remove(dayCampEntity);
        childEntity.setDayCamps(childEntity.getDayCamps().stream().filter(dayCamp -> !dayCamp.getId().equals(dayCampEntity.getId())).collect(Collectors.toSet()));

        dayCampDao.save(dayCampEntity);
    }

    @Override
    public List<DayCampEntity> getAll() {
        return dayCampDao.findAll();
    }

}
