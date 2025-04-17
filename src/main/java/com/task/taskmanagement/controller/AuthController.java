package com.task.taskmanagement.controller;

import com.task.taskmanagement.dto.request.LoginRequest;
import com.task.taskmanagement.dto.request.SignupRequest;
import com.task.taskmanagement.dto.response.MessageResponse;
import com.task.taskmanagement.dto.response.JwtResponse;
import com.task.taskmanagement.model.*;
import com.task.taskmanagement.model.enums.Role;
import com.task.taskmanagement.repository.OrganisationRepository;
import com.task.taskmanagement.repository.UserRepository;
import com.task.taskmanagement.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    OrganisationRepository organisationRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenProvider.generateToken(authentication);
        
        org.springframework.security.core.userdetails.UserDetails userDetails = 
                (org.springframework.security.core.userdetails.UserDetails) authentication.getPrincipal();
        
        String role = userDetails.getAuthorities().stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse("ROLE_MEMBER");
        
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();

        return ResponseEntity.ok(JwtResponse.builder()
                .accessToken(jwt)
                .tokenType("Bearer")
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(role)
                .build());
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Erreur: Le nom d'utilisateur est déjà pris!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Erreur: L'email est déjà utilisé!"));
        }

        Organisation organisation = organisationRepository.findById(signUpRequest.getOrganizationId().toString())
                .orElseThrow(() -> new RuntimeException("Organisation non trouvée"));

        String role = "ROLE_MEMBER";
        if (signUpRequest.getRole() != null && signUpRequest.getRole().equals("admin")) {
            role = "ROLE_ADMIN";
        }

        User user;
        String encodedPassword = encoder.encode(signUpRequest.getPassword());

        if (role.equals("ROLE_ADMIN")) {
            Admin admin = Admin.builder()
                    .username(signUpRequest.getUsername())
                    .password(encodedPassword)
                    .name(signUpRequest.getName())
                    .email(signUpRequest.getEmail())
                    .organisationId(organisation.getId())
                    .role(role)
                    .build();
            
            user = admin;
        } else if (signUpRequest.getMemberType().equals("Employee")) {
            Employee employee = Employee.builder()
                    .username(signUpRequest.getUsername())
                    .password(encodedPassword)
                    .name(signUpRequest.getName())
                    .email(signUpRequest.getEmail())
                    .organisationId(organisation.getId())
                    .role(role)
                    .score(0)
                    .build();
            
            user = employee;
        } else {
            Volunteer volunteer = Volunteer.builder()
                    .username(signUpRequest.getUsername())
                    .password(encodedPassword)
                    .name(signUpRequest.getName())
                    .email(signUpRequest.getEmail())
                    .organisationId(organisation.getId())
                    .role(role)
                    .score(0)
                    .build();
            
            user = volunteer;
        }

        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("Utilisateur enregistré avec succès!"));
    }
}