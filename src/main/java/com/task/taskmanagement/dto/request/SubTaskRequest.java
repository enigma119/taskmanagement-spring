package com.task.taskmanagement.dto.request;

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
public class SubTaskRequest {
    @NotBlank(message = "La description est obligatoire")
    private String description;
    
    @NotNull(message = "La durée estimée est obligatoire")
    private Integer estimatedDuration;
}