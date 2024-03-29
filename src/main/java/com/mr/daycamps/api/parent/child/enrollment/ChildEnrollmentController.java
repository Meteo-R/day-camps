package com.mr.daycamps.api.parent.child.enrollment;

import com.mr.daycamps.api.authentication.LoggedUserUtil;
import com.mr.daycamps.api.school.daycamp.DayCampsResponse;
import com.mr.daycamps.domain.authentication.Parent;
import com.mr.daycamps.domain.parent.child.enrollment.ChildEnrollmentService;
import com.mr.daycamps.domain.parent.child.enrollment.Enrollment;
import com.mr.daycamps.infrastructure.enrollment.DayCampEntity;
import com.mr.daycamps.infrastructure.enrollment.TimelineLocation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/enrollments")
@AllArgsConstructor
class ChildEnrollmentController {

    private final LoggedUserUtil loggedUserUtil;
    private final ChildEnrollmentService childEnrollmentService;
    private final EnrollmentApiMapper enrollmentMapper;

    @GetMapping(
            path = "/possible-day-camps/{childId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasRole('PARENT')")
    public ResponseEntity<?> getPossibleDayCampForChild(@PathVariable(name = "childId") Long childId) {
        Parent parent = loggedUserUtil.getLoggedParent();
        childEnrollmentService.validateChildAgainstParent(childId, parent);
        List<DayCampEntity> possibleDayCampsForChild = childEnrollmentService.getPossibleDayCampsForChild(childId);
        DayCampsResponse possibleDayCampsResponse = enrollmentMapper.mapToDayCampResponse(possibleDayCampsForChild);
        return ResponseEntity.ok(possibleDayCampsResponse);
    }

    @PostMapping(
            path = "/{childId}/{dayCampId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasRole('PARENT')")
    public ResponseEntity<?> enrollChild(@PathVariable(name = "childId") Long childId, @PathVariable(name = "dayCampId") Long dayCampId) {
        Parent parent = loggedUserUtil.getLoggedParent();
        childEnrollmentService.validateChildAgainstParent(childId, parent);
        childEnrollmentService.enrollChild(childId, dayCampId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PARENT')")
    public ResponseEntity<?> getAllChildrenEnrollments(@RequestParam(required = false) List<TimelineLocation> timelineLocation) {
        Parent parent = loggedUserUtil.getLoggedParent();
        List<Enrollment> enrollments = childEnrollmentService.getEnrollments(parent);
        EnrollmentsResponse enrollmentsResponse = enrollmentMapper.mapToEnrollmentsResponse(enrollments, timelineLocation);
        return ResponseEntity.ok(enrollmentsResponse);
    }

    @DeleteMapping(path = "/{childId}/{dayCampId}")
    @PreAuthorize("hasRole('PARENT')")
    public ResponseEntity<?> unenrollChild(@PathVariable(name = "childId") Long childId, @PathVariable(name = "dayCampId") Long dayCampId) {
        Parent parent = loggedUserUtil.getLoggedParent();
        childEnrollmentService.validateChildAgainstParent(childId, parent);
        childEnrollmentService.unenrollChild(childId, dayCampId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(
            path = "/{childId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasRole('PARENT')")
    public ResponseEntity<?> isChildEnrolledInAnyDayCamp(@PathVariable(name = "childId") Long childId) {
        Parent parent = loggedUserUtil.getLoggedParent();
        boolean isChildEnrolledInAnyDayCamp = childEnrollmentService.isChildEnrolledInAnyDayCamp(parent, childId);
        ChildEnrolledInAnyDayCampResponse response = enrollmentMapper.mapToIsChildEnrolledResponse(isChildEnrolledInAnyDayCamp);

        return ResponseEntity.ok(response);
    }

}
