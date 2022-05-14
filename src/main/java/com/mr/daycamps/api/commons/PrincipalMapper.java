package com.mr.daycamps.api.commons;

import com.mr.daycamps.domain.authentication.Parent;
import com.mr.daycamps.domain.authentication.UserDetailsImpl;
import org.springframework.stereotype.Component;

@Component
public class PrincipalMapper {

    private final RoleAuthorityMapper roleAuthorityMapper;

    public PrincipalMapper(RoleAuthorityMapper roleAuthorityMapper) {
        this.roleAuthorityMapper = roleAuthorityMapper;
    }

    public Parent mapParent(UserDetailsImpl parentPrincipal) {
        return Parent.builder()
                .setUsername(parentPrincipal.getUsername())
                .setEmail(parentPrincipal.getEmail())
                .setPhone(parentPrincipal.getPhone())
                .setRole(roleAuthorityMapper.mapToRole(parentPrincipal.getAuthorities().iterator().next().getAuthority()))
                .setFirstName(parentPrincipal.getFirstName())
                .setLastName(parentPrincipal.getLastName())
                .build();
    }
}
