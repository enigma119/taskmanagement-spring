package com.task.taskmanagement.model;

import jakarta.persistence.*;
import lombok.experimental.SuperBuilder;
import lombok.NoArgsConstructor;

@Entity
@SuperBuilder
@NoArgsConstructor
@DiscriminatorValue("EMPLOYEE")
public class Employee extends Member {
}
