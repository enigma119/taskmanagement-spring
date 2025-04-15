package com.task.taskmanagement.model;

import java.util.ArrayList;
import java.util.List;

import com.task.taskmanagement.model.enums.TaskStatus;

import jakarta.persistence.*;
import lombok.experimental.SuperBuilder;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@DiscriminatorValue("MEMBER")
public class Member extends User {

    private Integer score = 0;
    
    @OneToMany(mappedBy = "assignedMember", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Task> tasks = new ArrayList<>();

    public void addTask(Task task) {
        if (tasks == null) {
            tasks = new ArrayList<>();
        }
        tasks.add(task);
        task.setAssignedMember(this);
    }
    
    public void completeTask(Task task) {
        if (tasks.contains(task) && task.getStatus() == TaskStatus.DONE) {
            this.score += task.getType().getPoints();
        }
    }

    public void addToolToTask(Task task, Tool tool) {
        if (tasks.contains(task)) {
            task.addTool(tool);
        }
    }

    public void listTools(Task task) {
        if (tasks.contains(task)) {
            task.getTools().forEach(Tool::displayInfo);
        }
    }

    public void addCommentToTask(Task task, String comment) {
        if (tasks.contains(task)) {
            task.setComment(comment);
        }
    }
}
