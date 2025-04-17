package com.task.taskmanagement.controller;

import com.task.taskmanagement.dto.request.CommentRequest;
import com.task.taskmanagement.dto.request.StatusUpdateRequest;
import com.task.taskmanagement.dto.request.SubTaskRequest;
import com.task.taskmanagement.dto.response.TaskResponse;
import com.task.taskmanagement.dto.response.ToolResponse;
import com.task.taskmanagement.dto.response.UserResponse;
import com.task.taskmanagement.model.Task;
import com.task.taskmanagement.service.MemberService;
import com.task.taskmanagement.service.TaskMappingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/member")
@PreAuthorize("hasRole('ROLE_MEMBER')")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final TaskMappingService taskMappingService;

    @GetMapping("/profile")
    public ResponseEntity<UserResponse> getUserProfile(Authentication authentication) {
        return ResponseEntity.ok(memberService.getCurrentMemberProfile(authentication));
    }

    @GetMapping("/tasks")
    public ResponseEntity<List<TaskResponse>> getUserTasks(Authentication authentication) {
        List<Task> tasks = memberService.getMemberTasks(authentication);
        List<TaskResponse> taskDTOs = tasks.stream()
                .map(taskMappingService::convertToTaskResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(taskDTOs);
    }

    @PostMapping("/task/{taskId}/tool/{toolId}")
    public ResponseEntity<TaskResponse> addToolToTask(
            Authentication authentication,
            @PathVariable String taskId,
            @PathVariable String toolId) {
        Task task = memberService.addToolToTask(authentication, taskId, toolId);
        return ResponseEntity.ok(taskMappingService.convertToTaskResponse(task));
    }

    @GetMapping("/task/{taskId}/tools")
    public ResponseEntity<List<ToolResponse>> getTaskTools(
            Authentication authentication,
            @PathVariable String taskId) {
        return ResponseEntity.ok(memberService.getTaskTools(authentication, taskId));
    }

    @PutMapping("/task/{taskId}/status")
    public ResponseEntity<TaskResponse> updateTaskStatus(
            Authentication authentication,
            @PathVariable String taskId,
            @Valid @RequestBody StatusUpdateRequest request) {
        Task task = memberService.updateTaskStatus(authentication, taskId, request.getStatus());
        return ResponseEntity.ok(taskMappingService.convertToTaskResponse(task));
    }

    @PutMapping("/task/{taskId}/comment")
    public ResponseEntity<TaskResponse> addCommentToTask(
            Authentication authentication,
            @PathVariable String taskId,
            @Valid @RequestBody CommentRequest request) {
        Task task = memberService.addCommentToTask(authentication, taskId, request);
        return ResponseEntity.ok(taskMappingService.convertToTaskResponse(task));
    }
    
    @GetMapping("/task/{taskId}/progress")
    public ResponseEntity<Double> getTaskProgress(
            Authentication authentication,
            @PathVariable String taskId) {
        return ResponseEntity.ok(memberService.getTaskProgress(authentication, taskId));
    }
    
    @PostMapping("/task/{taskId}/subtask")
    public ResponseEntity<TaskResponse> addSubTask(
            Authentication authentication,
            @PathVariable String taskId,
            @Valid @RequestBody SubTaskRequest request) {
        Task task = memberService.addSubTask(authentication, taskId, request);
        return ResponseEntity.ok(taskMappingService.convertToTaskResponse(task));
    }
    
    @GetMapping("/task/{taskId}/subtasks")
    public ResponseEntity<List<TaskResponse>> getSubTasks(
            Authentication authentication,
            @PathVariable String taskId) {
        List<Task> subTasks = memberService.getSubTasks(authentication, taskId);
        List<TaskResponse> subTaskDTOs = subTasks.stream()
                .map(taskMappingService::convertToTaskResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(subTaskDTOs);
    }
}