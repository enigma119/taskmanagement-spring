package com.task.taskmanagement.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String id;
    private String username;
    private String name;
    private String email;
    private String role;
    private String userType;
    private Integer score;
    
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private OrganisationResponse organisation;
}