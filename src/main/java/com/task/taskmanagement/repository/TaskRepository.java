package com.task.taskmanagement.repository;

import com.task.taskmanagement.model.Task;
import com.task.taskmanagement.model.enums.TaskStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends MongoRepository<Task, String> {
    List<Task> findByOrganisationId(String organisationId);
    
    List<Task> findByAssignedMemberId(String memberId);
    
    List<Task> findByStatus(TaskStatus status);
    
    List<Task> findByParentTaskId(String parentTaskId);
    
    @Query("{ 'parentTask' : null, 'organisation._id' : ?0 }")
    List<Task> findRootTasksByOrganisationId(String organisationId);
}