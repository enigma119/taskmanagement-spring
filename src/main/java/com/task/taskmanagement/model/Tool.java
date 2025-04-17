package com.task.taskmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

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

    @Field("organisation_id")
    private String organisationId;

    @Field("used_in_task_ids")
    private List<String> usedInTaskIds = new ArrayList<>();

    public abstract String getType();
}