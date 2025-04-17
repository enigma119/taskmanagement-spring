package com.task.taskmanagement.model;
import com.task.taskmanagement.model.enums.TaskCategory;
import com.task.taskmanagement.model.enums.TaskStatus;
import com.task.taskmanagement.model.enums.TaskType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "tasks")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {
    @Id
    private String id;
    
    private String description;
    private TaskType type;
    private TaskCategory category;
    private TaskStatus status;
    private String comment;
    private double progress;
    private int score;
    
    @Field("assigned_member_id")
    private String assignedMemberId;
    
    @Field("organisation_id")
    private String organisationId;
    
    @Field("parent_task_id")
    private String parentTaskId;
    
    @Field("sub_task_ids")
    @Builder.Default
    private List<String> subTaskIds = new ArrayList<>();
    
    @Field("tool_ids")
    @Builder.Default
    private List<String> toolIds = new ArrayList<>();
    
    @Transient
    public boolean isLeaf() {
        return subTaskIds.isEmpty();
    }
    
    public void addSubTaskId(String taskId) {
        subTaskIds.add(taskId);
    }
    
    public void addToolId(String toolId) {
        toolIds.add(toolId);
    }
    
    public int calculateTotalScore() {
        if (isLeaf()) {
            return score;
        } else {
            return score; // This will need to be updated when fetching sub-tasks
        }
    }
    
    public double calculateProgress() {
        if (status == TaskStatus.DONE) {
            return 100.0;
        }
        
        if (isLeaf()) {
            return status == TaskStatus.IN_PROGRESS ? 50.0 : 0.0;
        } else {
            if (subTaskIds.isEmpty()) return 0.0;
            return subTaskIds.stream()
                    .mapToDouble(taskId -> {
                        // This is a placeholder implementation. You might want to fetch the task by ID and call its calculateProgress method
                        return 0.0; // Placeholder return, actual implementation needed
                    })
                    .average()
                    .orElse(0.0);
        }
    }
    
    public void updateStatus(TaskStatus newStatus) {
        this.status = newStatus;
        
        if (newStatus == TaskStatus.DONE) {
            subTaskIds.forEach(taskId -> {
                // This is a placeholder implementation. You might want to fetch the task by ID and call its updateStatus method
            });
        }
        
        recalculateProgress();
    }
    
    private void recalculateProgress() {
        this.progress = calculateProgress();
        
        if (parentTaskId != null) {
            // This is a placeholder implementation. You might want to fetch the parent task by ID and call its recalculateProgress method
        }
    }
    
    public void addTool(Tool tool) {
        // This is a placeholder implementation. You might want to add the tool to the list of tool IDs
    }
}