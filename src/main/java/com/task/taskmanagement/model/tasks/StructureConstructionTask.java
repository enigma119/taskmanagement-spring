package com.task.taskmanagement.model.tasks;
import com.task.taskmanagement.model.Task;
import com.task.taskmanagement.model.enums.TaskCategory;
import com.task.taskmanagement.model.enums.TaskType;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tasks")
public class StructureConstructionTask extends Task {
    
    public StructureConstructionTask() {
        super();
        setType(TaskType.STRUCTURE_CONSTRUCTION);
        updateScore();
    }
    
    public void updateScore() {
        if (getCategory() == TaskCategory.BASIC) {
            setScore(10);
        } else {
            setScore(40);
        }
    }
}