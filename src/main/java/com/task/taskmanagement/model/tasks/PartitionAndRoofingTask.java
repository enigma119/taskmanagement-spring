package com.task.taskmanagement.model.tasks;
import com.task.taskmanagement.model.Task;
import com.task.taskmanagement.model.enums.TaskCategory;
import com.task.taskmanagement.model.enums.TaskType;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tasks")
public class PartitionAndRoofingTask extends Task {
    
    public PartitionAndRoofingTask() {
        super();
        setType(TaskType.PARTITION_AND_ROOFING);
        updateScore();
    }
    
    public void updateScore() {
        if (getCategory() == TaskCategory.BASIC) {
            setScore(7);
        } else {
            setScore(25);
        }
    }
}