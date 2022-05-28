package com.mr.daycamps.api.parent.child;

import com.mr.daycamps.domain.parent.child.Child;
import com.mr.daycamps.infrastructure.enrollment.ChildEntity;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
class ChildApiMapper {

    Child mapAddChildRequest(AddUpdateChildRequest addChildRequest) {
        return Child.builder()
                .setFirstName(addChildRequest.getFirstName())
                .setLastName(addChildRequest.getLastName())
                .build();
    }

    ChildrenResponse mapToChildrenResponse(List<ChildEntity> children) {
        List<ChildResponse> childrenResponse = children.stream()
                .map(this::mapToChildResponse)
                .sorted(Comparator.comparing(ChildResponse::getId))
                .collect(Collectors.toList());

        return ChildrenResponse.builder()
                .setChildren(childrenResponse)
                .build();
    }

    ChildResponse mapToChildResponse(ChildEntity childEntity) {
        return ChildResponse.builder()
                .setId(childEntity.getId())
                .setFirstName(childEntity.getFirstName())
                .setLastName(childEntity.getLastName())
                .build();
    }
}
