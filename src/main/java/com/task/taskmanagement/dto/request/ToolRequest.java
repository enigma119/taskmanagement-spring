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
public class ToolRequest {
    @NotBlank(message = "Le nom est obligatoire")
    private String name;
    
    private boolean available = true;
    
    @NotNull(message = "L'identifiant de l'organisation est obligatoire")
    private Long organisationId;
    
}
