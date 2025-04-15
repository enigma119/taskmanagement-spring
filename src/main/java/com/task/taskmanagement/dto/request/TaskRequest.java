package com.task.taskmanagement.dto.request;

import com.task.taskmanagement.model.enums.TaskStatus;
import com.task.taskmanagement.model.enums.TypeTask;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequest {
    @NotBlank(message = "La description est obligatoire")
    private String description;
    
    @NotNull(message = "Le type de tâche est obligatoire")
    private TypeTask type;
    
    private TaskStatus status;
    
    private String comment;
    
    private Long assignedMemberId;
    
    @NotNull(message = "L'identifiant de l'organisation est obligatoire")
    private Long organisationId;
}