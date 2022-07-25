package com.mr.daycamps.infrastructure.users;

import com.mr.daycamps.domain.authentication.Parent;
import com.mr.daycamps.domain.parent.child.Child;
import com.mr.daycamps.infrastructure.enrollment.ChildEntity;

import java.util.List;

public interface ParentRepository {

    ChildEntity addChild(Parent parent, Child child);

    List<ChildEntity> getChildren(Parent parent);

    void updateChild(Parent parent, Long childId, Child childUpdateData);

    void deleteChild(Parent parent, Long childId);

    ChildEntity getChild(Parent parent, Long childId);
}
