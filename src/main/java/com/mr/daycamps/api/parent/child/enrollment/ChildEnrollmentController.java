package com.mr.daycamps.api.parent.child.enrollment;

import com.mr.daycamps.api.authentication.LoggedUserUtil;
import com.mr.daycamps.domain.authentication.Parent;
import com.mr.daycamps.domain.parent.child.enrollment.ChildEnrollmentService;
import com.mr.daycamps.domain.parent.child.enrollment.Enrollment;
import com.mr.daycamps.infrastructure.enrollment.TimelineLocation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
@AllArgsConstructor
class ChildEnrollmentController {

    private final LoggedUserUtil loggedUserUtil;
    private final ChildEnrollmentService childEnrollmentService;
    private final EnrollmentApiMapper enrollmentMapper;

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

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PARENT')")
    public ResponseEntity<?> getEnrollments(@RequestParam(required = false) List<TimelineLocation> timelineLocation) {
        Parent parent = loggedUserUtil.getLoggedParent();
        List<Enrollment> enrollments = childEnrollmentService.getEnrollments(parent);
        EnrollmentsResponse enrollmentsResponse = enrollmentMapper.mapToEnrollmentsResponse(enrollments, timelineLocation);
        return ResponseEntity.ok(enrollmentsResponse);
    }

    @DeleteMapping(path = "/{childId}/{dayCampId}")
    @PreAuthorize("hasRole('PARENT')")
    public ResponseEntity<?> unenrollChild(@PathVariable(name = "childId") Long childId, @PathVariable(name = "dayCampId") Long dayCampId) {
        childEnrollmentService.unenrollChild(childId, dayCampId);
        return ResponseEntity.noContent().build();
    }

}
