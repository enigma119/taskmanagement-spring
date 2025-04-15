package com.task.taskmanagement.controller;

import com.task.taskmanagement.dto.request.OrganisationRequest;
import com.task.taskmanagement.dto.response.OrganisationResponse;
import com.task.taskmanagement.dto.response.TaskResponse;
import com.task.taskmanagement.dto.response.ToolResponse;
import com.task.taskmanagement.dto.response.UserResponse;
import com.task.taskmanagement.model.enums.Role;
import com.task.taskmanagement.service.OrganisationService;
import com.task.taskmanagement.service.TaskService;
import com.task.taskmanagement.service.ToolService;
import com.task.taskmanagement.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {

    @Autowired
    private OrganisationService organisationService;

    @Autowired
    private UserService userService;

    @Autowired
    private ToolService toolService;

    @Autowired
    private TaskService taskService;

    @PostMapping("/organisation")
    public ResponseEntity<OrganisationResponse> createOrganisation(@Valid @RequestBody OrganisationRequest request) {
        return ResponseEntity.ok(organisationService.createOrganisation(request));
    }

    @GetMapping("/organisation/{id}")
    public ResponseEntity<OrganisationResponse> getOrganisationInfo(@PathVariable Long id) {
        return ResponseEntity.ok(organisationService.getOrganisationInfo(id));
    }

    @GetMapping("/member/{id}")
    public ResponseEntity<UserResponse> getMemberById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getMemberById(id));
    }

    // Lister toutes les tâches d'une organisation
    @GetMapping("/organisation/{id}/tasks")
    public ResponseEntity<List<TaskResponse>> getTasksByOrganisationId(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTasksByOrganisationId(id));
    }

    // Rechercher une tâche par son ID
    @GetMapping("/task/{id}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    // Rechercher un outil par son ID
    @GetMapping("/tool/{id}")
    public ResponseEntity<ToolResponse> getToolById(@PathVariable Long id) {
        return ResponseEntity.ok(toolService.getToolById(id));
    }

    // Lister les outils disponibles d'une organisation
    @GetMapping("/organization/{id}/tools")
    public ResponseEntity<List<ToolResponse>> getAvailableTools(@PathVariable Long id) {
        return ResponseEntity.ok(toolService.getAvailableTools(id));
    }
}