package com.task.taskmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.HashSet;
import java.util.Set;

@Document(collection = "users")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class User {
    @Id
    private String id;

    private String username;
    private String password;
    private String email;
    private String name;

    @Field("organisation_id")
    private String organisationId;

    private Set<String> roles = new HashSet<>();
}