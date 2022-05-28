package com.mr.daycamps.infrastructure.enrollment;

import com.mr.daycamps.domain.exception.ChildNotFoundException;
import com.mr.daycamps.domain.parent.child.Child;
import com.mr.daycamps.domain.parent.child.ChildRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
class ChildRepositoryImpl implements ChildRepository {

    private ChildDao childDao;
    private DayCampMapper dayCampMapper;

    @Override
    public Child getChildWithDayCamps(Long childId) {
        return childDao.findById(childId)
                .map(childEntity -> Child.builder()
                        .setId(childEntity.getId())
                        .setFirstName(childEntity.getFirstName())
                        .setLastName(childEntity.getLastName())
                        .setDayCamps(dayCampMapper.mapDayCamps(childEntity.getDayCamps()))
                        .build())
                .orElseThrow(() -> new ChildNotFoundException(childId));
    }
}
