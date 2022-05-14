package com.mr.daycamps.domain.parent;

import com.mr.daycamps.domain.authentication.Parent;
import com.mr.daycamps.domain.parent.child.Child;
import com.mr.daycamps.infrastructure.enrollment.ChildEntity;

import java.util.List;

public interface ParentRepository {

    ChildEntity addChild(Parent parent, Child child);

    List<ChildEntity> getChildren(Parent parent);

}
