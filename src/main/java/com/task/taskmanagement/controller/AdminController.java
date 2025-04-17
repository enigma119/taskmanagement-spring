package com.task.taskmanagement.controller;

import com.task.taskmanagement.dto.request.OrganisationRequest;
import com.task.taskmanagement.dto.request.SubTaskRequest;
import com.task.taskmanagement.dto.response.OrganisationResponse;
import com.task.taskmanagement.dto.response.TaskResponse;
import com.task.taskmanagement.dto.response.ToolResponse;
import com.task.taskmanagement.dto.response.UserResponse;
import com.task.taskmanagement.model.Task;
import com.task.taskmanagement.model.enums.TaskStatus;
import com.task.taskmanagement.service.OrganisationService;
import com.task.taskmanagement.service.TaskMappingService;
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
    
    @Autowired
    private TaskMappingService taskMappingService;

    @PostMapping("/organisation")
    public ResponseEntity<OrganisationResponse> createOrganisation(@Valid @RequestBody OrganisationRequest request) {
        return ResponseEntity.ok(organisationService.createOrganisation(request));
    }

    @GetMapping("/organisation/{id}")
    public ResponseEntity<OrganisationResponse> getOrganisationInfo(@PathVariable String id) {
        return ResponseEntity.ok(organisationService.getOrganisationInfo(id));
    }

    @GetMapping("/member/{id}")
    public ResponseEntity<UserResponse> getMemberById(@PathVariable String id) {
        return ResponseEntity.ok(userService.getMemberById(id));
    }

    // Lister toutes les tâches d'une organisation
    @GetMapping("/organisation/{id}/tasks")
    public ResponseEntity<List<TaskResponse>> getTasksByOrganisationId(@PathVariable String id) {
        List<Task> tasks = taskService.getTasksByOrganisationId(id);
        List<TaskResponse> taskDTOs = tasks.stream()
                .map(taskMappingService::convertToTaskResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(taskDTOs);
    }
    
    // Lister les tâches principales d'une organisation
    @GetMapping("/organisation/{id}/root-tasks")
    public ResponseEntity<List<TaskResponse>> getRootTasksByOrganisationId(@PathVariable String id) {
        List<Task> tasks = taskService.getRootTasksByOrganisationId(id);
        List<TaskResponse> taskDTOs = tasks.stream()
                .map(taskMappingService::convertToTaskResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(taskDTOs);
    }

    // Rechercher une tâche par son ID
    @GetMapping("/task/{id}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable String id) {
        Task task = taskService.getTaskById(id);
        return ResponseEntity.ok(taskMappingService.convertToTaskResponse(task));
    }
    
    // Voir le progrès d'une tâche
    @GetMapping("/task/{id}/progress")
    public ResponseEntity<Double> getTaskProgress(@PathVariable String id) {
        Task task = taskService.getTaskById(id);
        return ResponseEntity.ok(task.calculateProgress());
    }
    
    // Ajouter une sous-tâche
    @PostMapping("/task/{id}/subtask")
    public ResponseEntity<TaskResponse> addSubTask(
            @PathVariable String id,
            @Valid @RequestBody SubTaskRequest request) {
        Task parentTask = taskService.getTaskById(id);
        
        Task subTask = new Task();
        subTask.setDescription(request.getDescription());
        subTask.setType(parentTask.getType());
        subTask.setCategory(parentTask.getCategory());
        subTask.setStatus(TaskStatus.PLANNED);
        
        Task createdSubTask = taskService.addSubTask(id, subTask);
        return ResponseEntity.ok(taskMappingService.convertToTaskResponse(createdSubTask));
    }
    
    // Obtenir les sous-tâches
    @GetMapping("/task/{id}/subtasks")
    public ResponseEntity<List<TaskResponse>> getSubTasks(@PathVariable String id) {
        List<Task> subTasks = taskService.getSubTasks(id);
        List<TaskResponse> subTaskDTOs = subTasks.stream()
                .map(taskMappingService::convertToTaskResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(subTaskDTOs);
    }

    // Rechercher un outil par son ID
    @GetMapping("/tool/{id}")
    public ResponseEntity<ToolResponse> getToolById(@PathVariable String id) {
        return ResponseEntity.ok(toolService.getToolById(id));
    }

    // Lister les outils disponibles d'une organisation
    // @GetMapping("/organization/{id}/tools")
    // public ResponseEntity<List<ToolResponse>> getAvailableTools(@PathVariable String id) {
        // return ResponseEntity.ok

    // Lister les outils disponibles d'une organisation
    @GetMapping("/organization/{id}/tools/available")
    public ResponseEntity<List<ToolResponse>> getAvailableTools(@PathVariable String id) {
        return ResponseEntity.ok(toolService.getAvailableTools(id));
    }
    
    // Mettre à jour le statut d'une tâche
    @PutMapping("/task/{id}/status")
    public ResponseEntity<TaskResponse> updateTaskStatus(
            @PathVariable String id,
            @RequestBody TaskStatus status) {
        Task updatedTask = taskService.updateTaskStatus(id, status);
        return ResponseEntity.ok(taskMappingService.convertToTaskResponse(updatedTask));
    }
    
    // Ajouter un outil à une tâche
    @PostMapping("/task/{taskId}/tool/{toolId}")
    public ResponseEntity<TaskResponse> addToolToTask(
            @PathVariable String taskId,
            @PathVariable String toolId) {
        Task updatedTask = taskService.addToolToTask(taskId, toolId);
        return ResponseEntity.ok(taskMappingService.convertToTaskResponse(updatedTask));
    }
    
    // Ajouter un commentaire à une tâche
    @PutMapping("/task/{id}/comment")
    public ResponseEntity<TaskResponse> addCommentToTask(
            @PathVariable String id,
            @RequestBody String comment) {
        Task updatedTask = taskService.addCommentToTask(id, comment);
        return ResponseEntity.ok(taskMappingService.convertToTaskResponse(updatedTask));
    }
}