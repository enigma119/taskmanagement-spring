package com.task.taskmanagement.service;

import com.task.taskmanagement.dto.request.CommentRequest;
import com.task.taskmanagement.dto.request.StatusUpdateRequest;
import com.task.taskmanagement.dto.response.TaskResponse;
import com.task.taskmanagement.dto.response.ToolResponse;
import com.task.taskmanagement.dto.response.UserResponse;
import com.task.taskmanagement.model.Member;
import com.task.taskmanagement.model.Task;
import com.task.taskmanagement.model.Tool;
import com.task.taskmanagement.model.User;
import com.task.taskmanagement.model.enums.TaskStatus;
import com.task.taskmanagement.repository.TaskRepository;
import com.task.taskmanagement.repository.ToolRepository;
import com.task.taskmanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final ToolRepository toolRepository;
    private final UserService userService;
    private final TaskService taskService;
    private final ToolService toolService;

    public Member getCurrentMember(Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé"));

        if (!(user instanceof Member)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "L'utilisateur n'est pas un membre");
        }

        return (Member) user;
    }

    public UserResponse getCurrentMemberProfile(Authentication authentication) {
        Member member = getCurrentMember(authentication);
        return userService.convertToUserResponse(member);
    }

    public List<TaskResponse> getMemberTasks(Authentication authentication) {
        Member member = getCurrentMember(authentication);
        List<Task> tasks = taskRepository.findByAssignedMemberId(member.getId());
        return tasks.stream()
                .map(taskService::convertToTaskResponse)
                .collect(Collectors.toList());
    }

    public TaskResponse addToolToTask(Authentication authentication, Long taskId, Long toolId) {
        Member member = getCurrentMember(authentication);
        Task task = validateTaskOwner(taskId, member);
        Tool tool = toolRepository.findById(toolId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Outil non trouvé"));

        if (!tool.isAvailable()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cet outil n'est pas disponible");
        }

        task.addTool(tool);
        tool.setAvailable(false);
        
        task = taskRepository.save(task);
        toolRepository.save(tool);
        
        return taskService.convertToTaskResponse(task);
    }

    public List<ToolResponse> getTaskTools(Authentication authentication, Long taskId) {
        Member member = getCurrentMember(authentication);
        Task task = validateTaskOwner(taskId, member);
        
        return task.getUsedTools().stream()
                .map(toolService::convertToToolResponse)
                .collect(Collectors.toList());
    }

    public TaskResponse updateTaskStatus(Authentication authentication, Long taskId, StatusUpdateRequest request) {
        Member member = getCurrentMember(authentication);
        Task task = validateTaskOwner(taskId, member);
        
        TaskStatus oldStatus = task.getStatus();
        task.setStatus(request.getStatus());
        
        if (request.getStatus() == TaskStatus.DONE && oldStatus != TaskStatus.DONE) {
            member.setScore(member.getScore() + task.getType().getPoints());
            userRepository.save(member);
        }
        
        task = taskRepository.save(task);
        return taskService.convertToTaskResponse(task);
    }

    public TaskResponse addCommentToTask(Authentication authentication, Long taskId, CommentRequest request) {
        Member member = getCurrentMember(authentication);
        Task task = validateTaskOwner(taskId, member);
        
        task.addComment(request.getComment());
        task = taskRepository.save(task);
        
        return taskService.convertToTaskResponse(task);
    }

    private Task validateTaskOwner(Long taskId, Member member) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tâche non trouvée"));

        if (!task.getAssignedMember().getId().equals(member.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cette tâche ne vous est pas assignée");
        }

        return task;
    }
} 