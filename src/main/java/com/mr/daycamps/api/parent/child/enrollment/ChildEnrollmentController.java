package com.mr.daycamps.api.parent.child.enrollment;

import com.mr.daycamps.domain.parent.child.enrollment.ChildEnrollmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/enrollments")
@AllArgsConstructor
class ChildEnrollmentController {

    private ChildEnrollmentService childEnrollmentService;

    @PostMapping(
            path = "/{childId}/{dayCampId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasRole('PARENT')")
    public ResponseEntity<?> enrollChild(@PathVariable(name = "childId") Long childId, @PathVariable(name = "dayCampId") Long dayCampId) {
        childEnrollmentService.enrollChild(childId, dayCampId);
        return ResponseEntity.noContent().build();
    }

}
