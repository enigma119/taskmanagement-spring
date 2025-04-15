package com.task.taskmanagement.model;

import java.util.ArrayList;
import java.util.List;

import com.task.taskmanagement.model.enums.TaskStatus;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Organisation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    @OneToMany(mappedBy = "organisation", cascade = CascadeType.ALL)
    @Builder.Default
    private List<User> users = new ArrayList<>();
    
    @OneToMany(mappedBy = "organisation", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Tool> tools = new ArrayList<>();
    
    @OneToMany(mappedBy = "organisation", cascade = CascadeType.ALL)
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
            if (task.getStatus() == TaskStatus.DONE) {
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