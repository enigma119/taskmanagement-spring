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
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

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
    
    @DBRef
    private Member assignedMember;
    
    @DBRef
    private Organisation organisation;
    
    @DBRef
    private Task parentTask;
    
    @DBRef
    private List<Task> subTasks = new ArrayList<>();
    
    @DBRef
    private List<Tool> tools = new ArrayList<>();
    
    @Transient
    public boolean isLeaf() {
        return subTasks.isEmpty();
    }
    
    public void addSubTask(Task task) {
        subTasks.add(task);
        task.setParentTask(this);
        recalculateProgress();
    }
    
    public int calculateTotalScore() {
        if (isLeaf()) {
            return score;
        } else {
            return subTasks.stream()
                    .mapToInt(Task::calculateTotalScore)
                    .sum();
        }
    }
    
    public double calculateProgress() {
        if (status == TaskStatus.DONE) {
            return 100.0;
        }
        
        if (isLeaf()) {
            return status == TaskStatus.IN_PROGRESS ? 50.0 : 0.0;
        } else {
            if (subTasks.isEmpty()) return 0.0;
            return subTasks.stream()
                    .mapToDouble(Task::calculateProgress)
                    .average()
                    .orElse(0.0);
        }
    }
    
    public void updateStatus(TaskStatus newStatus) {
        this.status = newStatus;
        
        if (newStatus == TaskStatus.DONE) {
            subTasks.forEach(subTask -> subTask.updateStatus(TaskStatus.DONE));
        }
        
        recalculateProgress();
    }
    
    private void recalculateProgress() {
        this.progress = calculateProgress();
        
        if (parentTask != null) {
            parentTask.recalculateProgress();
        }
    }
    
    public void addTool(Tool tool) {
        tools.add(tool);
        tool.setAvailable(false);
    }
}