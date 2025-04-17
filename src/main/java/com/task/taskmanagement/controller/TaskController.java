package com.task.taskmanagement.controller;

import com.task.taskmanagement.dto.request.CommentRequest;
import com.task.taskmanagement.dto.request.StatusUpdateRequest;
import com.task.taskmanagement.model.Task;
import com.task.taskmanagement.model.Tool;
import com.task.taskmanagement.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TaskController {

    private final TaskService taskService;
    
    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable String id) {
        Task task = taskService.getTaskById(id);
        return ResponseEntity.ok(task);
    }
    
    @GetMapping("/organisation/{organisationId}")
    public ResponseEntity<List<Task>> getTasksByOrganisation(@PathVariable String organisationId) {
        List<Task> tasks = taskService.getTasksByOrganisationId(organisationId);
        return ResponseEntity.ok(tasks);
    }
    
    @GetMapping("/organisation/{organisationId}/root")
    public ResponseEntity<List<Task>> getRootTasksByOrganisation(@PathVariable String organisationId) {
        List<Task> tasks = taskService.getRootTasksByOrganisationId(organisationId);
        return ResponseEntity.ok(tasks);
    }
    
    @GetMapping("/{id}/subtasks")
    public ResponseEntity<List<Task>> getSubTasks(@PathVariable String id) {
        List<Task> subTasks = taskService.getSubTasks(id);
        return ResponseEntity.ok(subTasks);
    }
    
    @GetMapping("/{id}/progress")
    public ResponseEntity<Double> getTaskProgress(@PathVariable String id) {
        Task task = taskService.getTaskById(id);
        return ResponseEntity.ok(task.calculateProgress());
    }
    
    @GetMapping("/{id}/score")
    public ResponseEntity<Integer> getTaskScore(@PathVariable String id) {
        Task task = taskService.getTaskById(id);
        return ResponseEntity.ok(task.calculateTotalScore());
    }
    
    @GetMapping("/{id}/tools")
    public ResponseEntity<List<Tool>> getTaskTools(@PathVariable String id) {
        Task task = taskService.getTaskById(id);
        return ResponseEntity.ok(task.getTools());
    }
    
    @PutMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('MEMBER', 'ADMIN')")
    public ResponseEntity<Task> updateTaskStatus(
            @PathVariable String id,
            @Valid @RequestBody StatusUpdateRequest request) {
        Task updatedTask = taskService.updateTaskStatus(id, request.getStatus());
        return ResponseEntity.ok(updatedTask);
    }
    
    @PostMapping("/{id}/subtask")
    @PreAuthorize("hasAnyRole('MEMBER', 'ADMIN')")
    public ResponseEntity<Task> addSubTask(
            @PathVariable String id,
            @Valid @RequestBody Task subTask) {
        Task createdSubTask = taskService.addSubTask(id, subTask);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSubTask);
    }
    
    @PostMapping("/{id}/tool/{toolId}")
    @PreAuthorize("hasAnyRole('MEMBER', 'ADMIN')")
    public ResponseEntity<Task> addToolToTask(
            @PathVariable String id,
            @PathVariable String toolId) {
        Task updatedTask = taskService.addToolToTask(id, toolId);
        return ResponseEntity.ok(updatedTask);
    }
    
    @PutMapping("/{id}/comment")
    @PreAuthorize("hasAnyRole('MEMBER', 'ADMIN')")
    public ResponseEntity<Task> addCommentToTask(
            @PathVariable String id,
            @Valid @RequestBody CommentRequest request) {
        Task updatedTask = taskService.addCommentToTask(id, request.getComment());
        return ResponseEntity.ok(updatedTask);
    }
}