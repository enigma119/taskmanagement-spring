package com.task.taskmanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganisationResponse {
    private Long id;
    private String name;
    private int memberCount;
    private int totalScore;
    private int completedTaskCount;
}
