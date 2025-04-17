package com.task.taskmanagement.model;

import org.springframework.data.mongodb.core.mapping.Document;
import lombok.experimental.SuperBuilder;
import lombok.NoArgsConstructor;

@Document(collection = "tools")
@SuperBuilder
@NoArgsConstructor
public class MechanicalTool extends Tool {
    @Override
    public String getType() {
        return "MECHANICAL";
    }
}
