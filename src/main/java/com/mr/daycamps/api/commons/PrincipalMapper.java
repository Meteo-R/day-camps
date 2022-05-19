package com.mr.daycamps.api.commons;

import com.mr.daycamps.domain.authentication.Parent;
import com.mr.daycamps.domain.authentication.School;
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

    public School mapSchool(UserDetailsImpl schoolPrincipal) {
        return School.builder()
                .setUsername(schoolPrincipal.getUsername())
                .setEmail(schoolPrincipal.getEmail())
                .setPhone(schoolPrincipal.getPhone())
                .setRole(roleAuthorityMapper.mapToRole(schoolPrincipal.getAuthorities().iterator().next().getAuthority()))
                .setName(schoolPrincipal.getSchoolName())
                .setAddress(schoolPrincipal.getSchoolAddress())
                .build();
    }
}
