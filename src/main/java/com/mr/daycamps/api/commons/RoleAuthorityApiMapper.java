package com.mr.daycamps.api.commons;

import com.mr.daycamps.domain.authentication.Role;
import org.springframework.stereotype.Component;

@Component
class RoleAuthorityApiMapper {

    public Role mapToRole(String authority) {
        return switch (authority) {
            case "ROLE_PARENT" -> Role.ROLE_PARENT;
            case "ROLE_SCHOOL" -> Role.ROLE_SCHOOL;
            default -> throw new IllegalArgumentException("Constant not found: " + authority);
        };
    }
}
