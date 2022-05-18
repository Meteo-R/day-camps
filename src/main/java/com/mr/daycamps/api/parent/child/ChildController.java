package com.mr.daycamps.api.parent.child;

import com.mr.daycamps.api.commons.PrincipalMapper;
import com.mr.daycamps.domain.authentication.Parent;
import com.mr.daycamps.domain.authentication.UserDetailsImpl;
import com.mr.daycamps.domain.parent.child.Child;
import com.mr.daycamps.domain.parent.ParentRepository;
import com.mr.daycamps.infrastructure.enrollment.ChildEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/parent/children")
class ChildController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChildController.class);

    private final PrincipalMapper principalMapper;
    private final ChildMapper childMapper;
    private final ParentRepository parentRepository;

    public ChildController(PrincipalMapper principalMapper, ChildMapper childMapper, ParentRepository parentRepository) {
        this.principalMapper = principalMapper;
        this.childMapper = childMapper;
        this.parentRepository = parentRepository;
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasRole('PARENT')")
    public ResponseEntity<?> addChild(@Valid @RequestBody AddUpdateChildRequest addChildRequest) {
        Child child = childMapper.mapAddChildRequest(addChildRequest);
        Parent parent = getLoggedParent();

        ChildEntity addedChild = parentRepository.addChild(parent, child);

        ChildResponse addChildResponse = childMapper.mapToChildResponse(addedChild);
        LOGGER.info("Child: " + addChildResponse.getFirstName() + " " + addChildResponse.getLastName()
                + " added with id: " + addChildResponse.getId());
        return ResponseEntity.ok(addChildResponse);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PARENT')")
    public ResponseEntity<?> getChildren() {
        Parent parent = getLoggedParent();

        List<ChildEntity> children = parentRepository.getChildren(parent);

        ChildrenResponse childrenResponse = childMapper.mapToChildrenResponse(children);
        LOGGER.info(childrenResponse.getChildren().size() + " children found for user " + parent.getUsername());
        return ResponseEntity.ok(childrenResponse);
    }

    private Parent getLoggedParent() {
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principalMapper.mapParent(principal);
    }

    @PutMapping(
            path = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasRole('PARENT')")
    public ResponseEntity<?> modifyChild(@PathVariable(name = "id") Long childId, @Valid @RequestBody AddUpdateChildRequest updateChildRequest) {
        Child childUpdateData = childMapper.mapAddChildRequest(updateChildRequest);
        Parent parent = getLoggedParent();

        parentRepository.updateChild(parent, childId, childUpdateData);

        return ResponseEntity.noContent().build();
    }

}
