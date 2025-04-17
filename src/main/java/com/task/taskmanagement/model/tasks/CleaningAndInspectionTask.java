package com.task.taskmanagement.model.tasks;

import com.task.taskmanagement.model.Task;
import com.task.taskmanagement.model.enums.TaskCategory;
import com.task.taskmanagement.model.enums.TaskType;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tasks")
public class CleaningAndInspectionTask extends Task {
    
    public CleaningAndInspectionTask() {
        super();
        setType(TaskType.CLEANING_AND_INSPECTION);
        updateScore();
    }
    
    public void updateScore() {
        if (getCategory() == TaskCategory.BASIC) {
            setScore(4);
        } else {
            setScore(15);
        }
    }
}