package com.task.taskmanagement.partie1;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Task {

    private String id;
    private String title;
    private String description;
    private int estimatedDuration;
    private TaskStatus status;
    private TaskType type;
    private String comment;
    private Member assignedMember;
    private List<Tool> usedTools;

    public Task(String id, String title, String description, int estimatedDuration, TaskStatus status, TaskType type) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.estimatedDuration = estimatedDuration;
        this.status = TaskStatus.PLANNED;
        this.type = type;
        this.usedTools = new ArrayList<>();
    }

    // implementation des getters et setters

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getEstimatedDuration() {
        return estimatedDuration;
    }

    public void setEstimatedDuration(int estimatedDuration) {
        this.estimatedDuration = estimatedDuration;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public TaskType getType() {
        return type;
    }

    public void setType(TaskType type) {
        this.type = type;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Member getMember() {
        return assignedMember;
    }

    public void setMember(Member member) {
        this.assignedMember = member;
    }

    //  Methode de manipulation de la tache
    public void assignTo(Member member) {
        this.assignedMember = member;
    }

    public void addTool(Tool tool) {
        //  verifier si l'outil n'est pas deja utilisé
        if(!usedTools.contains(tool)) {
            usedTools.add(tool);
            // changer  la disponibilité de l'outil
            tool.setAvailable(false);

        }
    }

    // recuperer la liste des outils utilisés
    public List<Tool> getUsedTools() {
        return usedTools;
    }

    // ajouter un commentaire a la tache
    public void addComment(String comment) {
        // pour ne pas ecraser les commentaire precedents
        this.comment = this.comment + "\n" + comment;
    }

    // afficher les infos d'une tache
    public void displayInfo() {
        System.out.println("ID: " + id);
        System.out.println("Description: " + description);
        System.out.println("Durée estimée: " + estimatedDuration + " heures");
        System.out.println("Type: " + type.getPoints() + " points)");
        System.out.println("Statut: " + status);
        System.out.println("Commentaire: " + comment);
        System.out.println("Membre assigné: " + (assignedMember != null ? assignedMember.getName() : "Non assigné"));
        System.out.println("Nombre d'outils utilisés: " + usedTools.size());
    }



    
}
