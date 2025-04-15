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

}
