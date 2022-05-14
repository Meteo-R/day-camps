package com.mr.daycamps.infrastructure.users;

import com.mr.daycamps.domain.authentication.Parent;
import com.mr.daycamps.domain.parent.ParentRepository;
import com.mr.daycamps.domain.parent.child.Child;
import com.mr.daycamps.infrastructure.enrollment.ChildEntity;
import com.mr.daycamps.infrastructure.enrollment.ChildJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class ParentRepositoryImpl implements ParentRepository {

    private final ParentJpaRepository parentJpaRepository;
    private final ChildJpaRepository childJpaRepository;

    public ParentRepositoryImpl(ParentJpaRepository parentJpaRepository, ChildJpaRepository childJpaRepository) {
        this.parentJpaRepository = parentJpaRepository;
        this.childJpaRepository = childJpaRepository;
    }

    @Override
    public ChildEntity addChild(Parent parent, Child child) {
        Optional<ParentEntity> parentEntityOptional = parentJpaRepository.findByUsername(parent.getUsername());
        return parentEntityOptional.map(parentEntity -> persistChild(child, parentEntity))
                .orElseThrow(() -> new IllegalStateException("Parent with username: " + parent.getUsername() + " not found in repository!"));
    }

    private ChildEntity persistChild(Child child, ParentEntity parentEntity) {
        ChildEntity childEntity = ChildEntity.builder()
                .setFirstName(child.getFirstName())
                .setLastName(child.getLastName())
                .setParent(parentEntity)
                .build();
        parentEntity.getChildren().add(childEntity);
        return childJpaRepository.save(childEntity);
    }

}
