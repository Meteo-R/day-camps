package com.mr.daycamps.api.authentication;

import com.mr.daycamps.api.commons.PrincipalApiMapper;
import com.mr.daycamps.domain.authentication.Parent;
import com.mr.daycamps.domain.authentication.School;
import com.mr.daycamps.domain.authentication.UserDetailsImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class LoggedUserUtil {

    private final PrincipalApiMapper principalMapper;

    public Parent getLoggedParent() {
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principalMapper.mapParent(principal);
    }

    public School getLoggedSchool() {
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principalMapper.mapSchool(principal);
    }

}
