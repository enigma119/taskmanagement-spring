package com.task.taskmanagement.service;

import com.task.taskmanagement.dto.request.OrganisationRequest;
import com.task.taskmanagement.dto.response.OrganisationResponse;
import com.task.taskmanagement.model.Organisation;
import com.task.taskmanagement.model.User;
import com.task.taskmanagement.model.enums.TaskStatus;
import com.task.taskmanagement.repository.OrganisationRepository;
import com.task.taskmanagement.repository.TaskRepository;
import com.task.taskmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrganisationService {

    @Autowired
    private OrganisationRepository organisationRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private TaskRepository taskRepository;

    public OrganisationResponse createOrganisation(OrganisationRequest request) {
        Organisation organisation = new Organisation();
        organisation.setName(request.getName());
        organisation = organisationRepository.save(organisation);
        
        return OrganisationResponse.builder()
                .id(organisation.getId())
                .name(organisation.getName())
                .memberCount(0)
                .totalScore(0)
                .completedTaskCount(0)
                .build();
    }

    public OrganisationResponse getOrganisationInfo(String id) {
        Organisation organisation = organisationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Organisation non trouvée"));

        // Calculate member count and total score
        int memberCount = 0;
        int totalScore = 0;
        for (String userId : organisation.getUserIds()) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
            if (user instanceof com.task.taskmanagement.model.Member) {
                memberCount++;
                totalScore += ((com.task.taskmanagement.model.Member) user).getScore();
            }
        }

        // Calculate completed task count
        int completedTaskCount = 0;
        for (String taskId : organisation.getTaskIds()) {
            com.task.taskmanagement.model.Task task = taskRepository.findById(taskId)
                    .orElseThrow(() -> new RuntimeException("Tâche non trouvée"));
            if (task.getStatus() == TaskStatus.DONE) {
                completedTaskCount++;
            }
        }

        return OrganisationResponse.builder()
                .id(organisation.getId())
                .name(organisation.getName())
                .memberCount(memberCount)
                .totalScore(totalScore)
                .completedTaskCount(completedTaskCount)
                .build();
    }
}
