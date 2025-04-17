package com.task.taskmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "tools")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class Tool {
    @Id
    private String id;

    private String name;
    private boolean available = true;

    @DBRef
    private Organisation organisation;

    @DBRef
    private List<Task> usedInTasks = new ArrayList<>();
}