package com.mr.daycamps.infrastructure.enrollment;

import com.mr.daycamps.domain.parent.child.Child;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
class ChildMapper {

    public Set<Child> mapChildren(Set<ChildEntity> children) {
        return children.stream()
                .map(this::mapChild)
                .collect(Collectors.toUnmodifiableSet());
    }

    private Child mapChild(ChildEntity childEntity) {
        return Child.builder()
                .setId(childEntity.getId())
                .setFirstName(childEntity.getFirstName())
                .setLastName(childEntity.getLastName())
                .build();
    }

}
