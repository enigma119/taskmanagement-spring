package com.task.taskmanagement.partie1;

public class TaskSequenceService {
    
    public Task createNextTaskInSequence(Task completedTask, Organisation organisation) {
        if (completedTask.getStatus() != TaskStatus.DONE || completedTask.getParentTask() != null) {
            return null; // Ne crée une nouvelle tâche que si la tâche actuelle est terminée et est une tâche principale
        }
        
        TaskType nextType = getNextTaskType(completedTask.getType());
        if (nextType == null) {
            return null; // Pas de tâche suivante dans la séquence
        }
        
        String newId = organisation.generateNextTaskId();
        Task nextTask = new Task(
            newId,
            getDescriptionForType(nextType),
            nextType,
            completedTask.isProfessional(),
            estimateDuration(nextType, completedTask.isProfessional()),
            TaskStatus.PLANNED
        );
        
        return nextTask;
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
    
    private int estimateDuration(TaskType type, boolean isProfessional) {
        // Durée estimée en heures, selon le type et la catégorie
        if (isProfessional) {
            switch (type) {
                case SITE_PREPARATION: return 24;
                case FOUNDATION: return 40;
                case STRUCTURE_CONSTRUCTION: return 60;
                case PARTITION_AND_ROOFING: return 48;
                case TECHNICAL_INSTALLATION: return 40;
                case FINISHING_AND_SECURITY: return 32;
                case CLEANING_AND_INSPECTION: return 16;
                default: return 8;
            }
        } else {
            switch (type) {
                case SITE_PREPARATION: return 8;
                case FOUNDATION: return 16;
                case STRUCTURE_CONSTRUCTION: return 24;
                case PARTITION_AND_ROOFING: return 16;
                case TECHNICAL_INSTALLATION: return 16;
                case FINISHING_AND_SECURITY: return 12;
                case CLEANING_AND_INSPECTION: return 8;
                default: return 4;
            }
        }
    }
}