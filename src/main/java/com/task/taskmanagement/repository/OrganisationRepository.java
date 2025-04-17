package com.task.taskmanagement.repository;

import com.task.taskmanagement.model.Organisation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganisationRepository extends MongoRepository<Organisation, String> {
    @Query("{ 'name' : ?0 }")
    Organisation findByName(String name);
    
    @Query("{ 'code' : ?0 }")
    Organisation findByCode(String code);
    
    @Query("{ 'ownerId' : ?0 }")
    List<Organisation> findByOwnerId(String ownerId);
}