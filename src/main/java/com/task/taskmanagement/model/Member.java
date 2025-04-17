package com.task.taskmanagement.model;

import java.util.ArrayList;
import java.util.List;

import com.task.taskmanagement.model.enums.TaskStatus;
import lombok.experimental.SuperBuilder;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Member extends User {

    private Integer score = 0;
    
    @DBRef
    private List<Task> tasks = new ArrayList<>();
}
