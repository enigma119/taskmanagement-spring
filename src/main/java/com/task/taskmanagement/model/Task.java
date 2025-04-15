package com.task.taskmanagement.model;

import com.task.taskmanagement.model.enums.*;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    private TypeTask type;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private TaskStatus status = TaskStatus.PLANNED;

    @Column(columnDefinition = "TEXT")
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member assignedMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organisation_id")
    private Organisation organisation;
    
    @ManyToMany
    @JoinTable(
        name = "task_tools",
        joinColumns = @JoinColumn(name = "task_id"),
        inverseJoinColumns = @JoinColumn(name = "tool_id")
    )
    @Builder.Default
    private List<Tool> tools = new ArrayList<>();

    public void addTool(Tool tool) {
        if (tools == null) {
            tools = new ArrayList<>();
        }
        tools.add(tool);
        
        if (tool.getUsedInTasks() == null) {
            tool.setUsedInTasks(new ArrayList<>());
        }
        if (!tool.getUsedInTasks().contains(this)) {
            tool.getUsedInTasks().add(this);
        }
    }
    
    public void removeTool(Tool tool) {
        tools.remove(tool);
        tool.getUsedInTasks().remove(this);
    }
    
    public void addComment(String newComment) {
        if (this.comment == null || this.comment.isEmpty()) {
            this.comment = newComment;
        } else {
            this.comment = this.comment + "\n" + newComment;
        }
    }
    
    public int getPoints() {
        return type.getPoints();
    }

    public List<Tool> getTools() {
        return tools;
    }

    public void displayInfo() {
        System.out.println("Task: " + description);
        System.out.println("Status: " + status);
        System.out.println("Type: " + type);
        System.out.println("Comment: " + comment);
    }
}
