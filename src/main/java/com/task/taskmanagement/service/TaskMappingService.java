package com.task.taskmanagement.service;

import com.task.taskmanagement.dto.response.TaskResponse;
import com.task.taskmanagement.dto.response.OrganisationResponse;
import com.task.taskmanagement.dto.response.ToolResponse;
import com.task.taskmanagement.dto.response.UserResponse;
import com.task.taskmanagement.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskMappingService {

    @Autowired
    private UserService userService;
    
    @Autowired
    private TaskService taskService;
    
    @Autowired
    private OrganisationService organisationService;

    public TaskResponse convertToTaskResponse(Task task) {
        TaskResponse.TaskResponseBuilder builder = TaskResponse.builder()
                .id(task.getId())
                .description(task.getDescription())
                .type(task.getType())
                .category(task.getCategory())
                .status(task.getStatus())
                .comment(task.getComment())
                .progress(task.getProgress())
                .score(task.getScore());
        
        if (task.getAssignedMemberId() != null) {
            UserResponse memberResponse = userService.getMemberById(task.getAssignedMemberId());
            
            UserResponse memberWithoutOrg = UserResponse.builder()
                    .id(memberResponse.getId())
                    .username(memberResponse.getUsername())
                    .name(memberResponse.getName())
                    .email(memberResponse.getEmail())
                    .role(memberResponse.getRole())
                    .userType(memberResponse.getUserType())
                    .score(memberResponse.getScore())
                    .build();
            builder.assignedMember(memberWithoutOrg);
        }
        
        if (task.getOrganisationId() != null) {
            OrganisationResponse orgResponse = organisationService.getOrganisationInfo(task.getOrganisationId());
            builder.organisation(orgResponse);
        }
        
        if (task.getParentTaskId() != null) {
            builder.parentTaskId(task.getParentTaskId());
        }
        
        List<ToolResponse> toolResponses = task.getToolIds().stream()
                .map(toolId -> {
                    ToolResponse toolResponse = new ToolResponse();
                    toolResponse.setId(toolId);
                    return toolResponse;
                })
                .collect(Collectors.toList());
        builder.tools(toolResponses);
        
        if (!task.getSubTaskIds().isEmpty()) {
            List<TaskResponse> subTaskDTOs = task.getSubTaskIds().stream()
                    .map(subTaskId -> convertToTaskResponse(taskService.getTaskById(subTaskId)))
                    .collect(Collectors.toList());
            builder.subTasks(subTaskDTOs);
        }
        
        return builder.build();
    }

    public List<TaskResponse> convertToTaskResponses(List<Task> tasks) {
        return tasks.stream()
                .map(this::convertToTaskResponse)
                .collect(Collectors.toList());
    }
}