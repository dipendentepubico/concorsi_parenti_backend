package it.dipendentepubico.concorsiparenti.rest.auth.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import it.dipendentepubico.concorsiparenti.domain.reqsponse.AuthenticateUserRequest;
import it.dipendentepubico.concorsiparenti.domain.reqsponse.AuthenticateUserResponse;
import it.dipendentepubico.concorsiparenti.jpa.auth.entity.RoleEnumEntity;
import it.dipendentepubico.concorsiparenti.jpa.auth.entity.RoleEntity;
import it.dipendentepubico.concorsiparenti.jpa.auth.entity.UserEntity;
import it.dipendentepubico.concorsiparenti.jpa.auth.repository.RoleRepository;
import it.dipendentepubico.concorsiparenti.jpa.auth.repository.UserRepository;
import it.dipendentepubico.concorsiparenti.jwt.JwtUtils;
import it.dipendentepubico.concorsiparenti.rest.auth.model.*;
import it.dipendentepubico.concorsiparenti.usecase.auth.AuthUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

/**
 * Endpoint per registrare ed autenticare-autorizzare utente
 */
@Tag(name = "Endpoint per autenticazione e autorizzazione" )
@RestController
@CrossOrigin(origins = "${concorsiparenti.frontend.cors.url}", allowCredentials = "true")
@RequestMapping("api/auth")
public class AuthController {
    public static final String ERROR_ROLE_IS_NOT_FOUND = "Error: Role is not found.";
    public static final String ERROR_USERNAME_IS_ALREADY_TAKEN = "Error: Username is already taken!";
    public static final String ERROR_EMAIL_IS_ALREADY_IN_USE = "Error: Email is already in use!";

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    AuthUseCase authUseCase;
    @Autowired
    JwtUtils jwtUtils;
    @PostMapping("/signin")
    public ResponseEntity<LoginResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        AuthenticateUserRequest userRequest = new AuthenticateUserRequest(loginRequest.getUsername(), loginRequest.getPassword());
        AuthenticateUserResponse userResponse = authUseCase.authenticate(userRequest);

        // genero il jsessionid che verrà inviato con il Set-Cookie
        request.getSession(true);

        return ResponseEntity.ok(new LoginResponse(userResponse.getJwt(),
                userResponse.getUserDetails().getId(),
                userResponse.getUserDetails().getUsername(),
                userResponse.getUserDetails().getEmail(),
                userResponse.getRoles()));
    }
    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new SignupResponse(ERROR_USERNAME_IS_ALREADY_TAKEN));
        }
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new SignupResponse(ERROR_EMAIL_IS_ALREADY_IN_USE));
        }

        UserEntity userEntity = new UserEntity(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<Role> strRoles = signUpRequest.getRole();
        Set<RoleEntity> roleEntities = new HashSet<>();
        if (strRoles == null) {
            RoleEntity userRoleEntity = roleRepository.findByName(RoleEnumEntity.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException(ERROR_ROLE_IS_NOT_FOUND));
            roleEntities.add(userRoleEntity);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case ADMIN:
                        RoleEntity adminRoleEntity = roleRepository.findByName(RoleEnumEntity.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException(ERROR_ROLE_IS_NOT_FOUND));
                        roleEntities.add(adminRoleEntity);
                        break;
                    case MODERATOR:
                        RoleEntity modRoleEntity = roleRepository.findByName(RoleEnumEntity.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException(ERROR_ROLE_IS_NOT_FOUND));
                        roleEntities.add(modRoleEntity);
                        break;
                    case USER:
                    default:
                        RoleEntity userRoleEntity = roleRepository.findByName(RoleEnumEntity.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException(ERROR_ROLE_IS_NOT_FOUND));
                        roleEntities.add(userRoleEntity);
                }
            });
        }
        userEntity.setRoles(roleEntities);

        authUseCase.registerUser(userEntity);

        return ResponseEntity.ok(new SignupResponse("User registered successfully!"));
    }
}