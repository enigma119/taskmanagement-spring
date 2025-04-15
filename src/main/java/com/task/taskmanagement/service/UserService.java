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

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserResponse getMemberById(Long id) {
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
                .roles(new ArrayList<>(user.getRoles()));

        OrganisationResponse orgResponse = null;
        if (user.getOrganisation() != null) {
            orgResponse = OrganisationResponse.builder()
                    .id(user.getOrganisation().getId())
                    .name(user.getOrganisation().getName())
                    .build();
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
}
