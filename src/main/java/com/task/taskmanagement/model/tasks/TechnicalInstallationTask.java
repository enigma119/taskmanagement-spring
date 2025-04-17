package com.task.taskmanagement.model.tasks;

import com.task.taskmanagement.model.Task;
import com.task.taskmanagement.model.enums.TaskCategory;
import com.task.taskmanagement.model.enums.TaskType;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tasks")
public class TechnicalInstallationTask extends Task {
    
    public TechnicalInstallationTask() {
        super();
        setType(TaskType.TECHNICAL_INSTALLATION);
        updateScore();
    }
    
    public void updateScore() {
        if (getCategory() == TaskCategory.BASIC) {
            setScore(8);
        } else {
            setScore(30);
        }
    }
}