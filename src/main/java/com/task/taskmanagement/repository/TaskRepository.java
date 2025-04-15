package com.task.taskmanagement.repository;

import com.task.taskmanagement.model.*;
import com.task.taskmanagement.model.enums.TaskStatus;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByOrganisationId(Long organisationId);
    
    List<Task> findByAssignedMemberId(Long memberId);
    
    List<Task> findByStatus(TaskStatus status);
    
    @Query("SELECT t FROM Task t WHERE t.organisation.id = :organisationId AND t.status = :status")
    List<Task> findByOrganisationIdAndStatus(@Param("organisationId") Long organisationId, @Param("status") TaskStatus status);
    
    @Query("SELECT t FROM Task t WHERE t.assignedMember.id = :memberId AND t.status = :status")
    List<Task> findByAssignedMemberIdAndStatus(@Param("memberId") Long memberId, @Param("status") TaskStatus status);
}
