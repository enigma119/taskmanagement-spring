package com.task.taskmanagement.service;

import com.task.taskmanagement.dto.response.OrganisationResponse;
import com.task.taskmanagement.dto.response.UserResponse;
import com.task.taskmanagement.model.*;
import com.task.taskmanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final OrganisationService organisationService;
    public UserResponse getMemberById(String id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "L'ID du membre ne peut pas être null");
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Membre non trouvé"));

        return convertToUserResponse(user);
    }

    public UserResponse convertToUserResponse(User user) {
        UserResponse.UserResponseBuilder builder = UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole());

        OrganisationResponse orgResponse = null;
        if (user.getOrganisationId() != null) {
            orgResponse = organisationService.getOrganisationInfo(user.getOrganisationId());
        }

        builder.organisation(orgResponse);

        if (user instanceof Admin) {
            builder.userType("Admin");
        } else if (user instanceof Member) {
            Member member = (Member) user;
            builder.userType("Member")
                    .score(member.getScore());

            if (member instanceof Employee) {
                builder.userType("Employee");
            } else if (member instanceof Volunteer) {
                builder.userType("Volunteer");
            }
        }

        return builder.build();
    }

    public List<UserResponse> getMembersByOrganisationId(String organisationId) {
        List<User> users = userRepository.findByOrganisationId(organisationId);
        return users.stream()
                .filter(user -> user instanceof Member)
                .map(this::convertToUserResponse)
                .collect(Collectors.toList());
    }
}
