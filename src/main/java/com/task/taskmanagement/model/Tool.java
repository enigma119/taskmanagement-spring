package com.task.taskmanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tool_type")
public abstract class Tool {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Le nom de l'outil est obligatoire")
    private String name;

    @Builder.Default
    private boolean available = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organisation_id")
    private Organisation organisation;

    @ManyToMany(mappedBy = "tools")
    private List<Task> usedInTasks = new ArrayList<>();

    public void displayInfo() {
        System.out.println("Tool: " + name);
        System.out.println("Available: " + available);
        System.out.println("Type: " + this.getClass().getSimpleName());
    }
}
