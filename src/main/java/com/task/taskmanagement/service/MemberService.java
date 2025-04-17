package com.task.taskmanagement.service;

import com.task.taskmanagement.dto.request.CommentRequest;
import com.task.taskmanagement.dto.request.StatusUpdateRequest;
import com.task.taskmanagement.dto.request.SubTaskRequest;
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
    private final TaskSequenceService taskSequenceService;

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

    public List<Task> getMemberTasks(Authentication authentication) {
        Member member = getCurrentMember(authentication);
        return taskRepository.findByAssignedMemberId(member.getId());
    }

    public Task addToolToTask(Authentication authentication, String taskId, String toolId) {
        Member member = getCurrentMember(authentication);
        Task task = validateTaskOwner(taskId, member);
        Tool tool = toolRepository.findById(toolId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Outil non trouvé"));

        if (!tool.isAvailable()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cet outil n'est pas disponible");
        }

        return taskService.addToolToTask(taskId, toolId);
    }

    public List<ToolResponse> getTaskTools(Authentication authentication, String taskId) {
        Member member = getCurrentMember(authentication);
        Task task = validateTaskOwner(taskId, member);
        
        return task.getTools().stream()
                .map(tool -> {
                    ToolResponse response = new ToolResponse();
                    response.setId(tool.getId());
                    response.setName(tool.getName());
                    response.setAvailable(tool.isAvailable());
                    // Autres propriétés de l'outil
                    return response;
                })
                .collect(Collectors.toList());
    }

    public Task updateTaskStatus(Authentication authentication, String taskId, TaskStatus newStatus) {
        Member member = getCurrentMember(authentication);
        Task task = validateTaskOwner(taskId, member);
        
        Task updatedTask = taskService.updateTaskStatus(taskId, newStatus);
        
        // Si la tâche est terminée et que le membre a gagné des points
        if (newStatus == TaskStatus.DONE) {
            member.setScore(member.getScore() + task.calculateTotalScore());
            userRepository.save(member);
        }
        
        return updatedTask;
    }

    public Task addCommentToTask(Authentication authentication, String taskId, CommentRequest request) {
        Member member = getCurrentMember(authentication);
        Task task = validateTaskOwner(taskId, member);
        
        return taskService.addCommentToTask(taskId, request.getComment());
    }
    
    public Double getTaskProgress(Authentication authentication, String taskId) {
        Member member = getCurrentMember(authentication);
        Task task = validateTaskOwner(taskId, member);
        
        return task.calculateProgress();
    }
    
    public Task addSubTask(Authentication authentication, String taskId, SubTaskRequest request) {
        Member member = getCurrentMember(authentication);
        Task parentTask = validateTaskOwner(taskId, member);
        
        Task subTask = new Task();
        subTask.setDescription(request.getDescription());
        subTask.setType(parentTask.getType());
        subTask.setCategory(parentTask.getCategory());
        subTask.setStatus(TaskStatus.PLANNED);
        subTask.setAssignedMember(member);
        
        return taskService.addSubTask(taskId, subTask);
    }
    
    public List<Task> getSubTasks(Authentication authentication, String taskId) {
        Member member = getCurrentMember(authentication);
        Task task = validateTaskOwner(taskId, member);
        
        return taskRepository.findByParentTaskId(taskId);
    }

    private Task validateTaskOwner(String taskId, Member member) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tâche non trouvée"));

        if (task.getAssignedMember() == null || !task.getAssignedMember().getId().equals(member.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cette tâche ne vous est pas assignée");
        }

        return task;
    }
}