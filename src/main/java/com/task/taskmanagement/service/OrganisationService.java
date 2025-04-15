package com.task.taskmanagement.service;

import com.task.taskmanagement.dto.request.OrganisationRequest;
import com.task.taskmanagement.dto.response.OrganisationResponse;
import com.task.taskmanagement.model.Organisation;
import com.task.taskmanagement.repository.OrganisationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrganisationService {

    @Autowired
    private OrganisationRepository organisationRepository;

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

    public OrganisationResponse getOrganisationInfo(Long id) {
        Organisation organisation = organisationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Organisation non trouvée"));

        return OrganisationResponse.builder()
                .id(organisation.getId())
                .name(organisation.getName())
                .memberCount(organisation.getMemberCount())
                .totalScore(organisation.getTotalScore())
                .completedTaskCount(organisation.getCompletedTaskCount())
                .build();
    }

}
