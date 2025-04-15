package com.task.taskmanagement.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.task.taskmanagement.dto.response.OrganisationResponse;
import com.task.taskmanagement.dto.response.TaskResponse;
import com.task.taskmanagement.dto.response.ToolResponse;
import com.task.taskmanagement.dto.response.UserResponse;
import com.task.taskmanagement.model.Task;
import com.task.taskmanagement.repository.TaskRepository;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    private final UserService userService;
    private final ToolService toolService;

    public TaskService(UserService userService, ToolService toolService) {
        this.userService = userService;
        this.toolService = toolService;
    }

    public TaskResponse convertToTaskResponse(Task task) {
        UserResponse assignedMemberResponse = null;
        if (task.getAssignedMember() != null) {
            assignedMemberResponse = userService.convertToUserResponse(task.getAssignedMember());
        }

        OrganisationResponse orgResponse = null;
        if (task.getOrganisation() != null) {
            orgResponse = OrganisationResponse.builder()
                    .id(task.getOrganisation().getId())
                    .name(task.getOrganisation().getName())
                    .build();
        }

        List<ToolResponse> toolResponses = task.getTools().stream()
                .map(toolService::convertToToolResponse)
                .collect(Collectors.toList());

        return TaskResponse.builder()
                .id(task.getId())
                .description(task.getDescription())
                .type(task.getType())
                .status(task.getStatus())
                .comment(task.getComment())
                .assignedMember(assignedMemberResponse)
                .organisation(orgResponse)
                .usedTools(toolResponses)
                .build();
    }

    public List<TaskResponse> getTasksByOrganisationId(Long organisationId) {
        List<Task> tasks = taskRepository.findByOrganisationId(organisationId);
        List<TaskResponse> taskResponses = tasks.stream()
                .map(this::convertToTaskResponse)
                .collect(Collectors.toList());
        return taskResponses;
    }

    public TaskResponse getTaskById(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Tâche introuvable avec l'ID : " + taskId));
        return convertToTaskResponse(task);
    }

}