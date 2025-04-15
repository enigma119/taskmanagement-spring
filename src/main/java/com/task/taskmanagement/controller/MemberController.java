package com.task.taskmanagement.controller;

import com.task.taskmanagement.dto.request.CommentRequest;
import com.task.taskmanagement.dto.request.StatusUpdateRequest;
import com.task.taskmanagement.dto.response.TaskResponse;
import com.task.taskmanagement.dto.response.ToolResponse;
import com.task.taskmanagement.dto.response.UserResponse;
import com.task.taskmanagement.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/member")
@PreAuthorize("hasRole('ROLE_MEMBER')")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/profile")
    public ResponseEntity<UserResponse> getUserProfile(Authentication authentication) {
        return ResponseEntity.ok(memberService.getCurrentMemberProfile(authentication));
    }

    @GetMapping("/tasks")
    public ResponseEntity<List<TaskResponse>> getUserTasks(Authentication authentication) {
        return ResponseEntity.ok(memberService.getMemberTasks(authentication));
    }

    @PostMapping("/task/{taskId}/tool/{toolId}")
    public ResponseEntity<TaskResponse> addToolToTask(
            Authentication authentication,
            @PathVariable Long taskId,
            @PathVariable Long toolId) {
        return ResponseEntity.ok(memberService.addToolToTask(authentication, taskId, toolId));
    }

    @GetMapping("/task/{taskId}/tools")
    public ResponseEntity<List<ToolResponse>> getTaskTools(
            Authentication authentication,
            @PathVariable Long taskId) {
        return ResponseEntity.ok(memberService.getTaskTools(authentication, taskId));
    }

    @PutMapping("/task/{taskId}/status")
    public ResponseEntity<TaskResponse> updateTaskStatus(
            Authentication authentication,
            @PathVariable Long taskId,
            @Valid @RequestBody StatusUpdateRequest request) {
        return ResponseEntity.ok(memberService.updateTaskStatus(authentication, taskId, request));
    }

    @PutMapping("/task/{taskId}/comment")
    public ResponseEntity<TaskResponse> addCommentToTask(
            Authentication authentication,
            @PathVariable Long taskId,
            @Valid @RequestBody CommentRequest request) {
        return ResponseEntity.ok(memberService.addCommentToTask(authentication, taskId, request));
    }
}