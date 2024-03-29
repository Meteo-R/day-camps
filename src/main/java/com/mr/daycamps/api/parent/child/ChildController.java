package com.mr.daycamps.api.parent.child;

import com.mr.daycamps.api.authentication.LoggedUserUtil;
import com.mr.daycamps.domain.authentication.Parent;
import com.mr.daycamps.domain.parent.child.Child;
import com.mr.daycamps.infrastructure.enrollment.ChildEntity;
import com.mr.daycamps.infrastructure.users.ParentRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/parent/children")
@AllArgsConstructor
class ChildController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChildController.class);

    private final LoggedUserUtil loggedUserUtil;
    private final ChildApiMapper childMapper;
    private final ParentRepository parentRepository;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasRole('PARENT')")
    public ResponseEntity<?> addChild(@Valid @RequestBody AddUpdateChildRequest addChildRequest) {
        Child child = childMapper.mapAddChildRequest(addChildRequest);
        Parent parent = loggedUserUtil.getLoggedParent();

        ChildEntity addedChild = parentRepository.addChild(parent, child);

        ChildResponse addChildResponse = childMapper.mapToChildResponse(addedChild);
        LOGGER.info("Child: " + addChildResponse.getFirstName() + " " + addChildResponse.getLastName()
                + " added with id: " + addChildResponse.getId());
        return ResponseEntity.ok(addChildResponse);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PARENT')")
    public ResponseEntity<?> getChildren() {
        Parent parent = loggedUserUtil.getLoggedParent();

        List<ChildEntity> children = parentRepository.getChildren(parent);

        ChildrenResponse childrenResponse = childMapper.mapToChildrenResponse(children);
        LOGGER.info(childrenResponse.getChildren().size() + " children found for user " + parent.getUsername());
        return ResponseEntity.ok(childrenResponse);
    }

    @PutMapping(
            path = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasRole('PARENT')")
    public ResponseEntity<?> modifyChild(@PathVariable(name = "id") Long childId, @Valid @RequestBody AddUpdateChildRequest updateChildRequest) {
        Child childUpdateData = childMapper.mapAddChildRequest(updateChildRequest);
        Parent parent = loggedUserUtil.getLoggedParent();

        parentRepository.updateChild(parent, childId, childUpdateData);
        LOGGER.info("Child with id " + childId + " updated successfully");
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasRole('PARENT')")
    public ResponseEntity<?> deleteChild(@PathVariable(name = "id") Long childId) {
        Parent parent = loggedUserUtil.getLoggedParent();
        parentRepository.deleteChild(parent, childId);
        LOGGER.info("Child deleted successfully");
        return ResponseEntity.noContent().build();
    }

}
