package com.task.taskmanagement.controller;

import com.task.taskmanagement.dto.request.OrganisationRequest;
import com.task.taskmanagement.dto.request.SubTaskRequest;
import com.task.taskmanagement.dto.request.CommentRequest;
import com.task.taskmanagement.dto.response.OrganisationResponse;
import com.task.taskmanagement.dto.response.TaskResponse;
import com.task.taskmanagement.dto.response.ToolResponse;
import com.task.taskmanagement.dto.response.UserResponse;
import com.task.taskmanagement.model.Task;
import com.task.taskmanagement.model.User;
import com.task.taskmanagement.model.enums.TaskStatus;
import com.task.taskmanagement.service.OrganisationService;
import com.task.taskmanagement.service.TaskMappingService;
import com.task.taskmanagement.service.TaskService;
import com.task.taskmanagement.service.ToolService;
import com.task.taskmanagement.service.UserService;
import com.task.taskmanagement.security.RequestUtils;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    private final OrganisationService organisationService;
    private final UserService userService;
    private final ToolService toolService;
    private final TaskService taskService;
    private final TaskMappingService taskMappingService;

    @Autowired
    public AdminController(
            OrganisationService organisationService,
            UserService userService,
            ToolService toolService,
            TaskService taskService,
            TaskMappingService taskMappingService) {
        this.organisationService = organisationService;
        this.userService = userService;
        this.toolService = toolService;
        this.taskService = taskService;
        this.taskMappingService = taskMappingService;
    }

    @GetMapping("/my-organisation")
    public ResponseEntity<OrganisationResponse> getMyOrganisation(HttpServletRequest request) {
        User admin = RequestUtils.getCurrentUser(request);
        OrganisationResponse response = organisationService.getOrganisationInfo(admin.getOrganisationId());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/organisation")
    public ResponseEntity<OrganisationResponse> createOrganisation(@Valid @RequestBody OrganisationRequest request) {
        logger.info("Creating new organisation: {}", request.getName());
        OrganisationResponse response = organisationService.createOrganisation(request);
        logger.debug("Created organisation: {}", response);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/organisation/{id}")
    public ResponseEntity<OrganisationResponse> getOrganisationInfo(@PathVariable String id) {
        return ResponseEntity.ok(organisationService.getOrganisationInfo(id));
    }

    // Lister tous les membres d'une organisation
    @GetMapping("/member/{id}")
    public ResponseEntity<UserResponse> getMemberById(@PathVariable String id) {
        return ResponseEntity.ok(userService.getMemberById(id));
    }

    // Lister tous les membres de mon organisation
    @GetMapping("/my-organisation/members")
    public ResponseEntity<List<UserResponse>> getMembersByOrganisationId(HttpServletRequest request) {
        User admin = RequestUtils.getCurrentUser(request);
        return ResponseEntity.ok(userService.getMembersByOrganisationId(admin.getOrganisationId()));
    }

    // Lister toutes les tâches d'une organisation
    @GetMapping("/organisation/tasks")
    public ResponseEntity<List<TaskResponse>> getTasksByOrganisationId(HttpServletRequest request) {
        User admin = RequestUtils.getCurrentUser(request);
        List<Task> tasks = taskService.getTasksByOrganisationId(admin.getOrganisationId());
        return ResponseEntity.ok(taskMappingService.convertToTaskResponses(tasks));
    }
    
    // Lister les tâches principales d'une organisation
    @GetMapping("/organisation/{id}/root-tasks")
    public ResponseEntity<List<TaskResponse>> getRootTasksByOrganisationId(@PathVariable String id) {
        List<Task> tasks = taskService.getRootTasksByOrganisationId(id);
        return ResponseEntity.ok(taskMappingService.convertToTaskResponses(tasks));
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
        return ResponseEntity.ok(taskMappingService.convertToTaskResponses(subTasks));
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
    @GetMapping("/organisation/{id}/tools/available")
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
            @Valid @RequestBody CommentRequest request) {
        Task updatedTask = taskService.addCommentToTask(id, request.getComment());
        return ResponseEntity.ok(taskMappingService.convertToTaskResponse(updatedTask));
    }
}