package com.task.taskmanagement.service;

import java.util.stream.Collectors;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.task.taskmanagement.dto.response.OrganisationResponse;
import com.task.taskmanagement.dto.response.ToolResponse;
import com.task.taskmanagement.model.ElectricTool;
import com.task.taskmanagement.model.MechanicalTool;
import com.task.taskmanagement.model.Tool;
import com.task.taskmanagement.repository.ToolRepository;

@Service
public class ToolService {

    @Autowired
    private ToolRepository toolRepository;

    public ToolResponse convertToToolResponse(Tool tool) {
        ToolResponse.ToolResponseBuilder builder = ToolResponse.builder()
                .id(tool.getId())
                .name(tool.getName())
                .available(tool.isAvailable());

        OrganisationResponse orgResponse = null;
        if (tool.getOrganisation() != null) {
            orgResponse = OrganisationResponse.builder()
                    .id(tool.getOrganisation().getId())
                    .name(tool.getOrganisation().getName())
                    .build();
        }

        builder.organisation(orgResponse);

        if (tool instanceof ElectricTool) {
            builder.toolType("Electric");
        } else if (tool instanceof MechanicalTool) {
            builder.toolType("Mechanical");
        }

        return builder.build();
    }

    public ToolResponse getToolById(Long toolId) {
        Tool tool = toolRepository.findById(toolId)
                .orElseThrow(() -> new RuntimeException("Outil introuvable avec l'ID : " + toolId));
        return convertToToolResponse(tool);
    }

    public List<ToolResponse> getAvailableTools(Long organisationId) {
        List<Tool> tools = toolRepository.findByOrganisationIdAndAvailable(organisationId, true);
        List<ToolResponse> toolResponses = tools.stream()
                .map(this::convertToToolResponse)
                .collect(Collectors.toList());
        return toolResponses;
    }

}