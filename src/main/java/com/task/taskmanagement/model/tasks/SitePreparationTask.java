package com.task.taskmanagement.model.tasks;
import com.task.taskmanagement.model.Task;
import com.task.taskmanagement.model.enums.TaskCategory;
import com.task.taskmanagement.model.enums.TaskType;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tasks")
public class SitePreparationTask extends Task {
    
    public SitePreparationTask() {
        super();
        setType(TaskType.SITE_PREPARATION);
        updateScore();
    }
    
    public void updateScore() {
        if (getCategory() == TaskCategory.BASIC) {
            setScore(5);
        } else {
            setScore(18);
        }
    }
}