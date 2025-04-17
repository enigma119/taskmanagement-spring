package com.task.taskmanagement.service;

import com.task.taskmanagement.model.Task;
import com.task.taskmanagement.model.enums.TaskCategory;
import com.task.taskmanagement.model.enums.TaskStatus;
import com.task.taskmanagement.model.enums.TaskType;
import com.task.taskmanagement.model.tasks.*;
import com.task.taskmanagement.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskSequenceService {

    private final TaskRepository taskRepository;
    
    @Autowired
    public TaskSequenceService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
    
    public Task createNextTaskInSequence(Task completedTask) {
        if (completedTask.getStatus() != TaskStatus.DONE || completedTask.getParentTask() != null) {
            return null;
        }
        
        TaskType nextType = getNextTaskType(completedTask.getType());
        if (nextType == null) {
            return null;
        }
        
        Task nextTask = createTaskByType(nextType, completedTask.getCategory());
        nextTask.setOrganisation(completedTask.getOrganisation());
        nextTask.setStatus(TaskStatus.PLANNED);
        
        return taskRepository.save(nextTask);
    }
    
    private TaskType getNextTaskType(TaskType currentType) {
        switch (currentType) {
            case SITE_PREPARATION:
                return TaskType.FOUNDATION;
            case FOUNDATION:
                return TaskType.STRUCTURE_CONSTRUCTION;
            case STRUCTURE_CONSTRUCTION:
                return TaskType.PARTITION_AND_ROOFING;
            case PARTITION_AND_ROOFING:
                return TaskType.TECHNICAL_INSTALLATION;
            case TECHNICAL_INSTALLATION:
                return TaskType.FINISHING_AND_SECURITY;
            case FINISHING_AND_SECURITY:
                return TaskType.CLEANING_AND_INSPECTION;
            default:
                return null;
        }
    }
    
    private Task createTaskByType(TaskType type, TaskCategory category) {
        Task task;
        
        switch (type) {
            case SITE_PREPARATION:
                task = new SitePreparationTask();
                break;
            case FOUNDATION:
                task = new FoundationTask();
                break;
            case STRUCTURE_CONSTRUCTION:
                task = new StructureConstructionTask();
                break;
            case PARTITION_AND_ROOFING:
                task = new PartitionAndRoofingTask();
                break;
            case TECHNICAL_INSTALLATION:
                task = new TechnicalInstallationTask();
                break;
            case FINISHING_AND_SECURITY:
                task = new FinishingAndSecurityTask();
                break;
            case CLEANING_AND_INSPECTION:
                task = new CleaningAndInspectionTask();
                break;
            default:
                throw new IllegalArgumentException("Type de tâche non supporté: " + type);
        }
        
        task.setCategory(category);
        task.setDescription(getDescriptionForType(type));
        return task;
    }
    
    private String getDescriptionForType(TaskType type) {
        switch (type) {
            case SITE_PREPARATION:
                return "Préparation du site";
            case FOUNDATION:
                return "Fondations";
            case STRUCTURE_CONSTRUCTION:
                return "Construction de la structure";
            case PARTITION_AND_ROOFING:
                return "Cloisons, fenêtres et toiture";
            case TECHNICAL_INSTALLATION:
                return "Installation des systèmes techniques";
            case FINISHING_AND_SECURITY:
                return "Finitions et sécurisation";
            case CLEANING_AND_INSPECTION:
                return "Nettoyage et réception";
            default:
                return "Tâche inconnue";
        }
    }
}