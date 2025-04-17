package com.task.taskmanagement.partie1;

import java.util.List;

public class Admin extends User {
    public Admin(String id, String name, Organisation organisation) {
        super(id, name, organisation);
    }

    // Affichage des informations de l'organisation
    public void displayOrganisationInfo() {
        organisation.displayInfo();
    }

    // Rechercher un membre par son id
    public Member searchMemberById(String memberId) {
        User user = organisation.findUserById(memberId);
        if (user instanceof Member) {
            return (Member) user;
        }
        return null;
    }

    // Lister les taches effectuées par une organisation et leur état
    public List<Task> listOrganisationTasks() {
        return organisation.getTasks();
    }

    // Rechercher une tache par son id
    public Task searchTaskById(String taskId) {
        return organisation.findTaskById(taskId);
    }

    // Rechercher un outil par son id
    public Tool searchToolById(String toolId) {
        return organisation.findToolById(toolId);
    }

    // Lister les outils disponibles
    public List<Tool> listAvailableTools() {
        return organisation.getAvailableTools();
    }

    // Ajouter une sous-tâche à une tâche existante
    public void addSubTaskToTask(Task parentTask, String description, int estimatedDuration) {
        if (parentTask == null) {
            System.out.println("Tâche parente non trouvée.");
            return;
        }
        
        String newId = organisation.generateNextTaskId();
        Task subTask = new Task(
            newId,
            description,
            parentTask.getType(),
            parentTask.isProfessional(),
            estimatedDuration,
            TaskStatus.PLANNED
        );
        
        parentTask.addSubTask(subTask);
        organisation.addTask(subTask);
        System.out.println("Sous-tâche ajoutée avec succès.");
    }

    // Voir le progrès d'une tâche
    public void viewTaskProgress(Task task) {
        if (task == null) {
            System.out.println("Tâche non trouvée.");
            return;
        }

        System.out.println("\n=== Progrès de la tâche: " + task.getDescription() + " ===");
        System.out.println("Progrès: " + String.format("%.2f", task.calculateProgress()) + "%");
        
        if (!task.getSubTasks().isEmpty()) {
            System.out.println("\nSous-tâches:");
            for (Task subTask : task.getSubTasks()) {
                System.out.println("- " + subTask.getDescription() + ": " 
                    + String.format("%.2f", subTask.calculateProgress()) + "%");
            }
        }
    }
}