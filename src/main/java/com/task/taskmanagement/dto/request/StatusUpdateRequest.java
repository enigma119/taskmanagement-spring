package com.task.taskmanagement.dto.request;

import com.task.taskmanagement.model.enums.TaskStatus;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatusUpdateRequest {
    @NotNull(message = "Le nouveau statut est obligatoire")
    private TaskStatus status;
}