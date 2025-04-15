package com.task.taskmanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToolResponse {
    private Long id;
    private String name;
    private boolean available;
    private String toolType;
    private OrganisationResponse organisation;

}