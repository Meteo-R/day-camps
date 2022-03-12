package com.mr.daycamps.api.test;

import org.springframework.security.access.prepost.PreAuthorize;
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
        return "Parent content.";
    }

    @GetMapping("/school")
    @PreAuthorize("hasRole('SCHOOL')")
    public String schoolAccess() {
        return "School content.";
    }

}
