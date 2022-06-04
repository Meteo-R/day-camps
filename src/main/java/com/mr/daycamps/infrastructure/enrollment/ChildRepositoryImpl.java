package com.mr.daycamps.infrastructure.enrollment;

import com.mr.daycamps.domain.exception.ChildNotFoundException;
import com.mr.daycamps.domain.parent.child.ChildRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
class ChildRepositoryImpl implements ChildRepository {

    private ChildDao childDao;

    @Override
    public ChildEntity getChild(Long childId) {
        return childDao.findById(childId).orElseThrow(() -> new ChildNotFoundException(childId));
    }
}
