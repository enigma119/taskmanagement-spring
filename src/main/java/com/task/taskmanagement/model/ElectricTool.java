package com.task.taskmanagement.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.experimental.SuperBuilder;
import lombok.NoArgsConstructor;

@Entity
@SuperBuilder
@NoArgsConstructor
@DiscriminatorValue("ELECTRIC")
public class ElectricTool extends Tool {

}
