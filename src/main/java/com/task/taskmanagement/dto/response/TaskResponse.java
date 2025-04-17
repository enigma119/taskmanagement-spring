package com.task.taskmanagement.dto.response;

import com.task.taskmanagement.model.enums.TaskCategory;
import com.task.taskmanagement.model.enums.TaskStatus;
import com.task.taskmanagement.model.enums.TaskType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponse {
    private String id;
    private String description;
    private TaskType type;
    private TaskCategory category;
    private TaskStatus status;
    private String comment;
    private double progress;
    private int score;
    private UserResponse assignedMember;
    private OrganisationResponse organisation;
    private List<ToolResponse> tools;
    private List<TaskResponse> subTasks;
    private String parentTaskId;
}