package com.task.taskmanagement.model.tasks;
import com.task.taskmanagement.model.Task;
import com.task.taskmanagement.model.enums.TaskCategory;
import com.task.taskmanagement.model.enums.TaskType;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tasks")
public class FoundationTask extends Task {
    
    public FoundationTask() {
        super();
        setType(TaskType.FOUNDATION);
        updateScore();
    }
    
    public void updateScore() {
        if (getCategory() == TaskCategory.BASIC) {
            setScore(9);
        } else {
            setScore(30);
        }
    }
}