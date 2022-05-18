package com.mr.daycamps.infrastructure.users;

import com.mr.daycamps.domain.authentication.Parent;
import com.mr.daycamps.domain.parent.ParentRepository;
import com.mr.daycamps.domain.parent.child.Child;
import com.mr.daycamps.infrastructure.enrollment.ChildEntity;
import com.mr.daycamps.infrastructure.enrollment.ChildDao;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
class ParentRepositoryImpl implements ParentRepository {

    public static final String PARENT_NOT_FOUND_MESSAGE = "Parent with username: %s not found in repository!";
    public static final String CHILD_NOT_FOUND_MESSAGE = "Parent with username: %s does not have child with id %d";

    private final ParentDao parentDao;
    private final ChildDao childDao;

    public ParentRepositoryImpl(ParentDao parentDao, ChildDao childDao) {
        this.parentDao = parentDao;
        this.childDao = childDao;
    }

    @Override
    public ChildEntity addChild(Parent parent, Child child) {
        Optional<ParentEntity> parentEntityOptional = parentDao.findByUsername(parent.getUsername());
        return parentEntityOptional.map(parentEntity -> saveChild(child, parentEntity))
                .orElseThrow(() -> new IllegalStateException(String.format(PARENT_NOT_FOUND_MESSAGE, parent.getUsername())));
    }

    @Override
    public List<ChildEntity> getChildren(Parent parent) {
        Optional<ParentEntity> parentEntityOptional = parentDao.findByUsername(parent.getUsername());
        
        return parentEntityOptional.map(parentEntity -> new ArrayList<>(parentEntity.getChildren()))
                .orElseThrow(() -> new IllegalStateException(String.format(PARENT_NOT_FOUND_MESSAGE, parent.getUsername())));
    }

    @Override
    public ChildEntity updateChild(Parent parent, Long id, Child childUpdateData) {
        Optional<ParentEntity> parentEntityOptional = parentDao.findByUsername(parent.getUsername());

        List<ChildEntity> childEntities = parentEntityOptional.map(parentEntity -> new ArrayList<>(parentEntity.getChildren()))
                .orElseThrow(() -> new IllegalStateException(String.format(PARENT_NOT_FOUND_MESSAGE, parent.getUsername())));

        ChildEntity foundChild = childEntities.stream()
                .filter(childEntity -> Objects.equals(childEntity.getId(), id))
                .findAny()
                .orElseThrow(() -> new IllegalStateException(String.format(CHILD_NOT_FOUND_MESSAGE, parent.getUsername(), id)));

        foundChild.setFirstName(childUpdateData.getFirstName());
        foundChild.setLastName(childUpdateData.getLastName());

        return childDao.save(foundChild);
    }

    private ChildEntity saveChild(Child child, ParentEntity parentEntity) {
        ChildEntity childEntity = ChildEntity.builder()
                .setFirstName(child.getFirstName())
                .setLastName(child.getLastName())
                .setParent(parentEntity)
                .build();
        parentEntity.getChildren().add(childEntity);
        return childDao.save(childEntity);
    }

}
