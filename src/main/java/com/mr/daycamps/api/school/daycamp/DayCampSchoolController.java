package com.mr.daycamps.api.school.daycamp;

import com.mr.daycamps.api.authentication.LoggedUserUtil;
import com.mr.daycamps.api.daycamp.DayCampApiMapper;
import com.mr.daycamps.domain.authentication.School;
import com.mr.daycamps.domain.school.daycamp.DayCamp;
import com.mr.daycamps.infrastructure.enrollment.DayCampEntity;
import com.mr.daycamps.infrastructure.enrollment.TimelineLocation;
import com.mr.daycamps.infrastructure.users.SchoolRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/school/daycamps")
@AllArgsConstructor
class DayCampSchoolController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DayCampSchoolController.class);

    private final LoggedUserUtil loggedUserUtil;
    private final DayCampApiMapper dayCampMapper;
    private final SchoolRepository schoolRepository;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasRole('SCHOOL')")
    public ResponseEntity<?> addDayCamp(@Valid @RequestBody AddDayCampRequest addDayCampRequest) {
        DayCamp dayCamp = dayCampMapper.mapAddDayCampRequest(addDayCampRequest);
        School school = loggedUserUtil.getLoggedSchool();

        DayCampEntity addedDayCamp = schoolRepository.addDayCamp(school, dayCamp);

        DayCampResponse addDayCampResponse = dayCampMapper.mapToAddedDayCampResponse(addedDayCamp);
        LOGGER.info("Day camp: " + addDayCampResponse.getName() + " " + " added with id: " + addDayCampResponse.getId());
        return ResponseEntity.ok(addDayCampResponse);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('SCHOOL')")
    public ResponseEntity<?> getDayCamps(@RequestParam(required = false) List<TimelineLocation> timelineLocation) {
        School school = loggedUserUtil.getLoggedSchool();
        Set<DayCampEntity> dayCamps = schoolRepository.getDayCamps(school);
        DayCampsResponse dayCampsResponse = dayCampMapper.mapToSchoolDayCampsResponse(dayCamps, timelineLocation);
        return ResponseEntity.ok(dayCampsResponse);
    }

}
