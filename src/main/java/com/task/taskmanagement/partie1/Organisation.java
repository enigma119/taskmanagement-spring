package com.task.taskmanagement.partie1;

import java.util.ArrayList;
import java.util.List;

public class Organisation {
    private String id;
    private String name;
    private List<User> users;
    private List<Tool> tools;
    private List<Task> tasks;
    private int nextTaskId = 1;

    public Organisation(String id, String name) {
        this.id = id;
        this.name = name;
        this.users = new ArrayList<>();
        this.tools = new ArrayList<>();
        this.tasks = new ArrayList<>();
    }

    // Getters and existing methods
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return new ArrayList<>(users);
    }

    public List<Tool> getTools() {
        return new ArrayList<>(tools);
    }

    public List<Task> getTasks() {
        return new ArrayList<>(tasks);
    }

    // Methods
    public String generateNextTaskId() {
        return String.valueOf(nextTaskId++);
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void addTool(Tool tool) {
        tools.add(tool);
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    // Méthode pour obtenir les tâches principales (sans parent)
    public List<Task> getRootTasks() {
        List<Task> rootTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getParentTask() == null) {
                rootTasks.add(task);
            }
        }
        return rootTasks;
    }

    // Méthode pour obtenir les tâches par type
    public List<Task> getTasksByType(TaskType type) {
        List<Task> filteredTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getType() == type) {
                filteredTasks.add(task);
            }
        }
        return filteredTasks;
    }

    // Méthode pour calculer le score total
    public int getTotalScore() {
        int totalScore = 0;
        for (User user : users) {
            if (user instanceof Member) {
                totalScore += ((Member) user).getScore();
            }
        }
        return totalScore;
    }

    // Méthode pour calculer le nombre de tâches terminées
    public int getCompletedTaskCount() {
        int count = 0;
        for (Task task : getRootTasks()) {
            if (task.getStatus() == TaskStatus.DONE) {
                count++;
            }
        }
        return count;
    }

    // Lookup methods
    public User getUserById(String id) {
        for (User user : users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }

    public Tool getToolById(String id) {
        for (Tool tool : tools) {
            if (tool.getId().equals(id)) {
                return tool;
            }
        }
        return null;
    }

    public Task getTaskById(String id) {
        for (Task task : tasks) {
            if (task.getId().equals(id)) {
                return task;
            }
        }
        return null;
    }

    // Méthode personnalisée
    public List<Tool> getAvailableTools() {
        List<Tool> availableTools = new ArrayList<>();
        for (Tool tool : tools) {
            if (tool.isAvailable()) {
                availableTools.add(tool);
            }
        }
        return availableTools;
    }

    // Afficher le nombre de membres
    public int getMemberCount() {
        int count = 0;
        for (User user : users) {
            if (user instanceof Member) {
                count++;
            }
        }
        return count;
    }

    // afficher les infos de l'organisation
    public void displayInfo() {
        System.out.println("\n=== Informations de l'organisation ===");
        System.out.println("ID: " + id);
        System.out.println("Nom: " + name);
        System.out.println("Nombre de membres: " + getMemberCount());
        System.out.println("Score total: " + getTotalScore());
        System.out.println("Nombre de tâches complétées: " + getCompletedTaskCount());
        System.out.println("Nombre de tâches en cours: " + getRootTasks().size());
    }

    public User findUserById(String userId) {
        return getUserById(userId);
    }

    public Task findTaskById(String taskId) {
        return getTaskById(taskId);
    }

    public Tool findToolById(String toolId) {
        return getToolById(toolId);
    }
}