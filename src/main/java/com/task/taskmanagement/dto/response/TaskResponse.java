package com.task.taskmanagement.dto.response;

import java.util.List;

import com.task.taskmanagement.model.enums.TaskStatus;
import com.task.taskmanagement.model.enums.TypeTask;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponse {
    private Long id;
    private String description;
    private TypeTask type;
    private TaskStatus status;
    private String comment;
    private UserResponse assignedMember;
    private OrganisationResponse organisation;
    private List<ToolResponse> usedTools;
}