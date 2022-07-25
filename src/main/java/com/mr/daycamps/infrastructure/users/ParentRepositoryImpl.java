package com.mr.daycamps.infrastructure.users;

import com.mr.daycamps.domain.authentication.Parent;
import com.mr.daycamps.domain.exception.ChildNotFoundException;
import com.mr.daycamps.domain.exception.EnrolledChildDeletionAttemptException;
import com.mr.daycamps.domain.exception.ParentNotFoundException;
import com.mr.daycamps.domain.parent.child.Child;
import com.mr.daycamps.infrastructure.enrollment.ChildDao;
import com.mr.daycamps.infrastructure.enrollment.ChildEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@AllArgsConstructor
class ParentRepositoryImpl implements ParentRepository {

    private final ParentDao parentDao;
    private final ChildDao childDao;

    @Override
    public ChildEntity addChild(Parent parent, Child child) {
        Optional<ParentEntity> parentEntityOptional = parentDao.findByUsername(parent.getUsername());
        return parentEntityOptional.map(parentEntity -> saveChild(child, parentEntity))
                .orElseThrow(() -> new ParentNotFoundException(parent.getUsername()));
    }

    @Override
    public List<ChildEntity> getChildren(Parent parent) {
        Optional<ParentEntity> parentEntityOptional = parentDao.findByUsername(parent.getUsername());

        return parentEntityOptional.map(parentEntity -> new ArrayList<>(parentEntity.getChildren()))
                .orElseThrow(() -> new ParentNotFoundException(parent.getUsername()));
    }

    @Override
    public void updateChild(Parent parent, Long childId, Child childUpdateData) {
        Optional<ParentEntity> parentEntityOptional = parentDao.findByUsername(parent.getUsername());

        List<ChildEntity> childEntities = parentEntityOptional.map(parentEntity -> new ArrayList<>(parentEntity.getChildren()))
                .orElseThrow(() -> new ParentNotFoundException(parent.getUsername()));

        ChildEntity foundChild = childEntities.stream()
                .filter(childEntity -> Objects.equals(childEntity.getId(), childId))
                .findAny()
                .orElseThrow(() -> new ChildNotFoundException(parent.getUsername(), childId));

        foundChild.setFirstName(childUpdateData.getFirstName());
        foundChild.setLastName(childUpdateData.getLastName());

        childDao.save(foundChild);
    }

    @Override
    public void deleteChild(Parent parent, Long childId) {
        Optional<ParentEntity> parentEntityOptional = parentDao.findByUsername(parent.getUsername());

        parentEntityOptional.ifPresentOrElse(
                parentEntity -> {
                    ChildEntity foundChild = parentEntity.getChildren().stream()
                            .filter(childEntity -> Objects.equals(childEntity.getId(), childId))
                            .findAny()
                            .orElseThrow(() -> new ChildNotFoundException(parent.getUsername(), childId));
                    if (foundChild.getDayCamps().size() == 0) {
                        parentEntity.getChildren().remove(foundChild);
                        parentDao.save(parentEntity);
                    } else {
                        throw new EnrolledChildDeletionAttemptException();
                    }
                },
                () -> {
                    throw new ParentNotFoundException(parent.getUsername());
                }
        );
    }

    @Override
    public ChildEntity getChild(Parent parent, Long childId) {
        Optional<ParentEntity> parentEntityOptional = parentDao.findByUsername(parent.getUsername());

        ArrayList<ChildEntity> children = parentEntityOptional.map(parentEntity -> new ArrayList<>(parentEntity.getChildren()))
                .orElseThrow(() -> new ParentNotFoundException(parent.getUsername()));

        return children.stream()
                .filter(child -> child.getId().equals(childId))
                .findAny()
                .orElseThrow(() -> new ChildNotFoundException(parent.getUsername(), childId));
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
