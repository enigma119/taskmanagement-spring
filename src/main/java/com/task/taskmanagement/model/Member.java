package com.task.taskmanagement.model;

import java.util.ArrayList;
import java.util.List;

import com.task.taskmanagement.model.enums.TaskStatus;
import lombok.experimental.SuperBuilder;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "users")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Member extends User {

    private Integer score = 0;
    
    @Field("task_ids")
    private List<String> taskIds = new ArrayList<>();
}
