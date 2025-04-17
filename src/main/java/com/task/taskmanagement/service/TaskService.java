package com.task.taskmanagement.service;

import com.task.taskmanagement.exception.ResourceNotFoundException;
import com.task.taskmanagement.model.Member;
import com.task.taskmanagement.model.Task;
import com.task.taskmanagement.model.Tool;
import com.task.taskmanagement.model.enums.TaskStatus;
import com.task.taskmanagement.repository.MemberRepository;
import com.task.taskmanagement.repository.TaskRepository;
import com.task.taskmanagement.repository.ToolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskSequenceService taskSequenceService;
    private final MemberRepository memberRepository;
    private final ToolRepository toolRepository;
    
    @Autowired
    public TaskService(TaskRepository taskRepository,
                      TaskSequenceService taskSequenceService,
                      MemberRepository memberRepository,
                      ToolRepository toolRepository) {
        this.taskRepository = taskRepository;
        this.taskSequenceService = taskSequenceService;
        this.memberRepository = memberRepository;
        this.toolRepository = toolRepository;
    }
    
    public Task getTaskById(String id) {
        return taskRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Tâche non trouvée avec l'ID: " + id));
    }
    
    public List<Task> getTasksByOrganisationId(String organisationId) {
        return taskRepository.findByOrganisationId(organisationId);
    }
    
    public List<Task> getRootTasksByOrganisationId(String organisationId) {
        return taskRepository.findRootTasksByOrganisationId(organisationId);
    }
    
    public List<Task> getSubTasks(String parentTaskId) {
        return taskRepository.findByParentTaskId(parentTaskId);
    }
    
    @Transactional
    public Task updateTaskStatus(String taskId, TaskStatus newStatus) {
        Task task = getTaskById(taskId);
        TaskStatus oldStatus = task.getStatus();
        
        task.updateStatus(newStatus);
        Task updatedTask = taskRepository.save(task);
        
        // Si la tâche est terminée et était une tâche principale, créer la tâche suivante
        if (newStatus == TaskStatus.DONE && oldStatus != TaskStatus.DONE && task.getParentTaskId() == null) {
            Task nextTask = taskSequenceService.createNextTaskInSequence(task);
            if (nextTask != null) {
                nextTask.setAssignedMemberId(task.getAssignedMemberId());
                taskRepository.save(nextTask);
                
                // Mettre à jour le score du membre
                if (task.getAssignedMemberId() != null) {
                    Member member = memberRepository.findById(task.getAssignedMemberId())
                        .orElseThrow(() -> new ResourceNotFoundException("Membre non trouvé"));
                    member.setScore(member.getScore() + task.calculateTotalScore());
                    memberRepository.save(member);
                }
            }
        }
        
        return updatedTask;
    }
    
    @Transactional
    public Task addSubTask(String parentTaskId, Task subTask) {
        Task parentTask = getTaskById(parentTaskId);
        
        subTask.setParentTaskId(parentTaskId);
        subTask.setOrganisationId(parentTask.getOrganisationId());
        subTask.setCategory(parentTask.getCategory());
        
        Task savedSubTask = taskRepository.save(subTask);
        
        parentTask.addSubTaskId(savedSubTask.getId());
        taskRepository.save(parentTask);
        
        return savedSubTask;
    }
    
    @Transactional
    public Task addToolToTask(String taskId, String toolId) {
        Task task = getTaskById(taskId);
        Tool tool = toolRepository.findById(toolId)
            .orElseThrow(() -> new ResourceNotFoundException("Outil non trouvé avec l'ID: " + toolId));
        if (!tool.isAvailable()) {
            throw new IllegalStateException("Cet outil n'est pas disponible");
        }
        
        task.addToolId(toolId);
        tool.getUsedInTaskIds().add(taskId);
        tool.setAvailable(false);
        
        toolRepository.save(tool);
        return taskRepository.save(task);
    }
    
    @Transactional
    public Task addCommentToTask(String taskId, String comment) {
        Task task = getTaskById(taskId);
        
        if (task.getComment() == null || task.getComment().isEmpty()) {
            task.setComment(comment);
        } else {
            task.setComment(task.getComment() + "\n" + comment);
        }
        
        return taskRepository.save(task);
    }
}