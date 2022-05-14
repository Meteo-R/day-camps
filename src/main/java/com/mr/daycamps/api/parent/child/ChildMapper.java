package com.mr.daycamps.api.parent.child;

import com.mr.daycamps.domain.parent.child.Child;
import com.mr.daycamps.infrastructure.enrollment.ChildEntity;
import org.springframework.stereotype.Component;

@Component
class ChildMapper {

    public Child mapAddChildRequest(AddChildRequest addChildRequest) {
        return Child.builder()
                .setFirstName(addChildRequest.getFirstName())
                .setLastName(addChildRequest.getLastName())
                .build();
    }

    public AddChildResponse mapToAddChildResponse(ChildEntity childEntity) {
        return AddChildResponse.builder()
                .setId(childEntity.getId())
                .setFirstName(childEntity.getFirstName())
                .setLastName(childEntity.getLastName())
                .build();
    }
}
