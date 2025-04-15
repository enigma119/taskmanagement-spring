package com.task.taskmanagement.repository;

import com.task.taskmanagement.model.*;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ToolRepository extends JpaRepository<Tool, Long> {
    List<Tool> findByOrganisationId(Long organisationId);
    
    List<Tool> findByAvailable(boolean available);
    
    @Query("SELECT t FROM Tool t WHERE t.organisation.id = :organisationId AND t.available = :available")
    List<Tool> findByOrganisationIdAndAvailable(@Param("organisationId") Long organisationId, @Param("available") boolean available);
}
