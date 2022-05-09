package com.mr.daycamps.api.test;

import com.mr.daycamps.domain.authentication.UserDetailsImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
class TestController {

    @GetMapping("/all")
    public String allAccess() {
        return "Public content.";
    }

    @GetMapping("/parent")
    @PreAuthorize("hasRole('PARENT')")
    public String parentAccess() {
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return "Parent content. You are logged in as: " + principal.getUsername();
    }

    @GetMapping("/school")
    @PreAuthorize("hasRole('SCHOOL')")
    public String schoolAccess() {
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return "School content. You are logged in as: " + principal.getUsername();
    }

}
