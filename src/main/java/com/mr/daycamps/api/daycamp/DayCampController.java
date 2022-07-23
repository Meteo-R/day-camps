package com.mr.daycamps.api.daycamp;

import com.mr.daycamps.api.school.daycamp.DayCampsResponse;
import com.mr.daycamps.domain.school.daycamp.DayCampRepository;
import com.mr.daycamps.infrastructure.enrollment.DayCampEntity;
import com.mr.daycamps.infrastructure.enrollment.TimelineLocation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/daycamps")
@AllArgsConstructor
class DayCampController {

    private final DayCampRepository dayCampRepository;
    private final DayCampApiMapper dayCampMapper;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getDayCamps(@RequestParam(required = false) List<TimelineLocation> timelineLocation) {
        List<DayCampEntity> dayCamps = dayCampRepository.getAll();
        DayCampsResponse allDayCampsResponse = dayCampMapper.mapToAllDayCampsResponse(dayCamps, timelineLocation);
        return ResponseEntity.ok(allDayCampsResponse);
    }

}
