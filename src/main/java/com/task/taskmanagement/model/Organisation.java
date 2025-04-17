package com.task.taskmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "organisations")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Organisation {
    @Id
    private String id;
    
    private String name;
    
    @Field("user_ids")
    @Builder.Default
    private List<String> userIds = new ArrayList<>();
    
    @Field("tool_ids")
    @Builder.Default
    private List<String> toolIds = new ArrayList<>();
    
    @Field("task_ids")
    @Builder.Default
    private List<String> taskIds = new ArrayList<>();

    public void addUserId(String userId) {
        userIds.add(userId);
    }
    
    public void addToolId(String toolId) {
        toolIds.add(toolId);
    }

    public void addTaskId(String taskId) {
        taskIds.add(taskId);
    }

    public int getMemberCount() {
        int count = 0;
        for (User user : users) {
            if (user instanceof Member) {
                count++;
            }
        }
        return count;
    }
    
    public int getTotalScore() {
        int totalScore = 0;
        for (User user : users) {
            if (user instanceof Member) {
                totalScore += ((Member) user).getScore();
            }
        }
        return totalScore;
    }
    
    public int getCompletedTaskCount() {
        int count = 0;
        for (Task task : tasks) {
            if (task.getStatus() == com.task.taskmanagement.model.enums.TaskStatus.DONE) {
                count++;
            }
        }
        return count;
    }

    public List<Tool> getAvailableTools() {
        List<Tool> availableTools = new ArrayList<>();
        for (Tool tool : tools) {
            if (tool.isAvailable()) {
                availableTools.add(tool);
            }
        }
        return availableTools;
    }
}