package com.task.taskmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

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
    
    @DBRef
    @Builder.Default
    private List<User> users = new ArrayList<>();
    
    @DBRef
    @Builder.Default
    private List<Tool> tools = new ArrayList<>();
    
    @DBRef
    @Builder.Default
    private List<Task> tasks = new ArrayList<>();

    public void addUser(User user) {
        users.add(user);
        user.setOrganisation(this);
    }
    
    public void addTool(Tool tool) {
        tools.add(tool);
        tool.setOrganisation(this);
    }

    public void addTask(Task task) {
        tasks.add(task);
        task.setOrganisation(this);
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