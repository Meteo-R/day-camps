package com.mr.daycamps.api.school.daycamp;

import com.mr.daycamps.api.commons.PrincipalMapper;
import com.mr.daycamps.domain.authentication.School;
import com.mr.daycamps.domain.authentication.UserDetailsImpl;
import com.mr.daycamps.domain.school.daycamp.DayCamp;
import com.mr.daycamps.infrastructure.enrollment.DayCampEntity;
import com.mr.daycamps.infrastructure.users.SchoolRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/school/daycamps")
@AllArgsConstructor
class DayCampController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DayCampController.class);

    private final PrincipalMapper principalMapper;
    private final DayCampMapper dayCampMapper;
    private final SchoolRepository schoolRepository;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasRole('SCHOOL')")
    public ResponseEntity<?> addDayCamp(@Valid @RequestBody AddDayCampRequest addDayCampRequest) {
        DayCamp dayCamp = dayCampMapper.mapAddDayCampRequest(addDayCampRequest);
        School school = getLoggedSchool();

        DayCampEntity addedDayCamp = schoolRepository.addDayCamp(school, dayCamp);

        DayCampResponse addDayCampResponse = dayCampMapper.mapToDayCampResponse(addedDayCamp);
        LOGGER.info("Day camp: " + addDayCampResponse.getName() + " " + " added with id: " + addDayCampResponse.getId());
        return ResponseEntity.ok(addDayCampResponse);
    }

    private School getLoggedSchool() {
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principalMapper.mapSchool(principal);
    }


}
