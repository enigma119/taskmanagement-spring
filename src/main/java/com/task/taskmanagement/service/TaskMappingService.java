package com.task.taskmanagement.service;

import com.task.taskmanagement.dto.response.TaskResponse;
import com.task.taskmanagement.dto.response.OrganisationResponse;
import com.task.taskmanagement.dto.response.ToolResponse;
import com.task.taskmanagement.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskMappingService {

    @Autowired
    private UserService userService;
    
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
        
        if (task.getAssignedMember() != null) {
            builder.assignedMember(userService.convertToUserResponse(task.getAssignedMember()));
        }
        
        if (task.getOrganisation() != null) {
            OrganisationResponse orgResponse = OrganisationResponse.builder()
                    .id(task.getOrganisation().getId())
                    .name(task.getOrganisation().getName())
                    .build();
            builder.organisation(orgResponse);
        }
        
        if (task.getParentTask() != null) {
            builder.parentTaskId(task.getParentTask().getId());
        }
        
        List<ToolResponse> toolResponses = task.getTools().stream()
                .map(tool -> {
                    ToolResponse toolResponse = new ToolResponse();
                    toolResponse.setId(tool.getId());
                    toolResponse.setName(tool.getName());
                    toolResponse.setAvailable(tool.isAvailable());
                    return toolResponse;
                })
                .collect(Collectors.toList());
        builder.tools(toolResponses);
        
        // Récursivité contrôlée pour les sous-tâches (un niveau seulement)
        if (!task.getSubTasks().isEmpty()) {
            List<TaskResponse> subTaskDTOs = task.getSubTasks().stream()
                    .map(subTask -> {
                        return TaskResponse.builder()
                                .id(subTask.getId())
                                .description(subTask.getDescription())
                                .type(subTask.getType())
                                .category(subTask.getCategory())
                                .status(subTask.getStatus())
                                .progress(subTask.calculateProgress())
                                .score(subTask.getScore())
                                .parentTaskId(task.getId())
                                .build();
                    })
                    .collect(Collectors.toList());
            builder.subTasks(subTaskDTOs);
        }
        
        return builder.build();
    }
}