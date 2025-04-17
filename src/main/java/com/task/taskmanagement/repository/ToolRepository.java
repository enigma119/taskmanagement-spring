package com.task.taskmanagement.repository;

import com.task.taskmanagement.model.Tool;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToolRepository extends MongoRepository<Tool, String> {
    List<Tool> findByOrganisationId(String organisationId);
    
    List<Tool> findByAvailable(boolean available);
    
    @Query("{ 'organisation._id' : ?0, 'available' : ?1 }")
    List<Tool> findByOrganisationIdAndAvailable(String organisationId, boolean available);
}