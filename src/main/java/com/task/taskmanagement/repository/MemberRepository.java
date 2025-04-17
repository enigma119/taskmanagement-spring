package com.task.taskmanagement.repository;

import com.task.taskmanagement.model.Member;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends MongoRepository<Member, String> {
    List<Member> findByOrganisationId(String organisationId);
    
    @Query("{ 'organisationId' : ?0, 'role' : ?1 }")
    List<Member> findByOrganisationIdAndRole(String organisationId, String role);
    
    @Query("{ 'organisationId' : ?0, 'email' : ?1 }")
    Member findByOrganisationIdAndEmail(String organisationId, String email);
}