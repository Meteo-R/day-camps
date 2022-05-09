package com.mr.daycamps.api.authentication;

import com.mr.daycamps.application.configuration.security.jwt.JwtUtils;
import com.mr.daycamps.domain.authentication.User;
import com.mr.daycamps.domain.authentication.UserDetailsImpl;
import com.mr.daycamps.domain.authentication.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/authentication")
class AuthenticationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final AuthenticationMapperFactory authenticationMapperFactory;

    public AuthenticationController(AuthenticationManager authenticationManager,
                                    UserRepository userRepository,
                                    JwtUtils jwtUtils,
                                    AuthenticationMapperFactory authenticationMapperFactory) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
        this.authenticationMapperFactory = authenticationMapperFactory;
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        } catch (BadCredentialsException e) {
            LOGGER.warn("Bad credentials!");
            return new ResponseEntity<>(new MessageResponse("Error: Bad credentials!"), HttpStatus.UNAUTHORIZED);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(LoginResponse.builder()
                .setToken(jwt)
                .setUsername(userDetails.getUsername())
                .setEmail(userDetails.getEmail())
                .setPhone(userDetails.getPhone())
                .setRoles(roles)
                .setFirstName(userDetails.getFirstName())
                .setLastName(userDetails.getLastName())
                .setSchoolName(userDetails.getSchoolName())
                .setSchoolAddress(userDetails.getSchoolAddress())
                .build());
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {

        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            LOGGER.warn("Username already taken: " + signUpRequest.getUsername());
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already in use!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            LOGGER.warn("Email already in use: " + signUpRequest.getEmail());
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        User user = authenticationMapperFactory.getMapper(signUpRequest).mapToUser(signUpRequest);

        userRepository.save(user);

        LOGGER.info("User " + user.getUsername() + " registered successfully.");
        return ResponseEntity.ok(new MessageResponse("User registered successfully."));
    }

}
