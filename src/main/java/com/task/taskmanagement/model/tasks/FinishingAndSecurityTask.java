package com.task.taskmanagement.model.tasks;

import com.task.taskmanagement.model.Task;
import com.task.taskmanagement.model.enums.TaskCategory;
import com.task.taskmanagement.model.enums.TaskType;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tasks")
public class FinishingAndSecurityTask extends Task {
    
    public FinishingAndSecurityTask() {
        super();
        setType(TaskType.FINISHING_AND_SECURITY);
        updateScore();
    }
    
    public void updateScore() {
        if (getCategory() == TaskCategory.BASIC) {
            setScore(6);
        } else {
            setScore(22);
        }
    }
}