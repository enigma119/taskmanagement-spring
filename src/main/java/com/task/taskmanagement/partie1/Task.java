package com.task.taskmanagement.partie1;

import java.util.ArrayList;
import java.util.List;

public class Task {
    private String id;
    private String description;
    private TaskType type;
    private boolean isProfessional;
    private int estimatedDuration;
    private TaskStatus status;
    private String comment;
    private Member assignedMember;
    private Task parentTask;
    private List<Task> subTasks;
    private List<Tool> usedTools;

    public Task(String id, String description, TaskType type, boolean isProfessional, int estimatedDuration, TaskStatus status) {
        this.id = id;
        this.description = description;
        this.type = type;
        this.isProfessional = isProfessional;
        this.estimatedDuration = estimatedDuration;
        this.status = status;
        this.subTasks = new ArrayList<>();
        this.usedTools = new ArrayList<>();
    }

    // Getters and setters

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskType getType() {
        return type;
    }

    public void setType(TaskType type) {
        this.type = type;
    }

    public boolean isProfessional() {
        return isProfessional;
    }

    public int getEstimatedDuration() {
        return estimatedDuration;
    }

    public void setEstimatedDuration(int estimatedDuration) {
        this.estimatedDuration = estimatedDuration;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Member getAssignedMember() {
        return assignedMember;
    }

    public void assignTo(Member member) {
        this.assignedMember = member;
    }

    public Task getParentTask() {
        return parentTask;
    }

    public void setParentTask(Task parentTask) {
        this.parentTask = parentTask;
    }

    public List<Task> getSubTasks() {
        return new ArrayList<>(subTasks);
    }

    public void addSubTask(Task task) {
        subTasks.add(task);
        task.setParentTask(this);
    }

    public List<Tool> getUsedTools() {
        return new ArrayList<>(usedTools);
    }

    public void addTool(Tool tool) {
        if (!usedTools.contains(tool)) {
            usedTools.add(tool);
            tool.setAvailable(false);
        }
    }

    public void addComment(String newComment) {
        if (this.comment == null || this.comment.isEmpty()) {
            this.comment = newComment;
        } else {
            this.comment = this.comment + "\n" + newComment;
        }
    }

    public int getPoints() {
        return type.getPoints(isProfessional);
    }

    public int calculateTotalPoints() {
        if (subTasks.isEmpty()) {
            return getPoints();
        } else {
            int total = 0;
            for (Task subTask : subTasks) {
                total += subTask.calculateTotalPoints();
            }
            return total;
        }
    }

    public double calculateProgress() {
        if (status == TaskStatus.DONE) {
            return 100.0;
        }
        
        if (subTasks.isEmpty()) {
            return status == TaskStatus.IN_PROGRESS ? 50.0 : 0.0;
        } else {
            double totalProgress = 0.0;
            for (Task subTask : subTasks) {
                totalProgress += subTask.calculateProgress();
            }
            return totalProgress / subTasks.size();
        }
    }

    public void displayInfo() {
        System.out.println("ID: " + id);
        System.out.println("Description: " + description);
        System.out.println("Type: " + type + " (" + (isProfessional ? "Professionnel" : "Basic") + ")");
        System.out.println("Points: " + getPoints());
        System.out.println("Durée estimée: " + estimatedDuration + " heures");
        System.out.println("Statut: " + status);
        System.out.println("Progrès: " + String.format("%.2f", calculateProgress()) + "%");
        System.out.println("Commentaire: " + comment);
        System.out.println("Membre assigné: " + (assignedMember != null ? assignedMember.getName() : "Non assigné"));
        System.out.println("Nombre d'outils utilisés: " + usedTools.size());
        System.out.println("Sous-tâches: " + subTasks.size());
    }

    // Nouveau methodes
    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
        
        // Si on marque la tâche comme terminée, toutes les sous-tâches sont aussi terminées
        if (status == TaskStatus.DONE) {
            for (Task subTask : subTasks) {
                subTask.setStatus(TaskStatus.DONE);
            }
        }
        
        // Si c'est une sous-tâche, vérifier si toutes les sous-tâches du parent sont terminées
        if (parentTask != null) {
            boolean allDone = true;
            for (Task siblingTask : parentTask.getSubTasks()) {
                if (siblingTask.getStatus() != TaskStatus.DONE) {
                    allDone = false;
                    break;
                }
            }
            if (allDone) {
                parentTask.setStatus(TaskStatus.DONE);
            }
        }
    }
}