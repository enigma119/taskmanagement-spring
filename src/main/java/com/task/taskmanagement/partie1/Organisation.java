package com.task.taskmanagement.partie1;

import java.util.ArrayList;
import java.util.List;

public class Organisation {
    private String id;
    private String name;
    private List<User> users;
    private List<Tool> tools;
    private List<Task> tasks;

    public Organisation(String id, String name) {
        this.id = id;
        this.name = name;
        this.users = new ArrayList<>();
        this.tools = new ArrayList<>();
        this.tasks = new ArrayList<>();
    }

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

    // Methode d'ajout
    public void addUser(User user) {
        users.add(user);
    }

    public void addTool(Tool tool) {
        tools.add(tool);
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    // Methode de recherche par id
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

    // Methode personnalisee
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

    // calculer le score total des membres
    public int getTotalScore() {
        int totalScore = 0;
        for (User user : users) {
            if (user instanceof Member) {
                totalScore += ((Member) user).getScore();
            }
        }
        return totalScore;
    }

    // Afficher le nombre de tâches
    public String getTaskCount() {
        int planedCount = 0;
        int inProgressCount = 0;
        int doneCount = 0;
        for (Task task : tasks) {
            if (task.getStatus() == TaskStatus.PLANNED) {
                planedCount++;
            } else if (task.getStatus() == TaskStatus.IN_PROGRESS) {
                inProgressCount++;
            } else if (task.getStatus() == TaskStatus.DONE) {
                doneCount++;
            }
        }
        return "Planifiées: " + planedCount + ", En cours: " + inProgressCount + ", Terminées: " + doneCount;
    }

    // afficher les infos de l'organisation
    public void displayInfo() {
        System.out.println("\n=== Informations de l'organisation ===");
        System.out.println("ID: " + id);
        System.out.println("Nom: " + name);
        System.out.println("Nombre de membres: " + getMemberCount());
        System.out.println("Score total: " + getTotalScore());
        System.out.println("Nombre de tâches: " + getTaskCount());
    }

    public User findUserById(String userId) {
        for (User user : users) {
            if (user.getId().equals(userId)) {
                return user;
            }
        }
        return null;
    }

    public Task findTaskById(String taskId) {
        for (Task task : tasks) {
            if (task.getId().equals(taskId)) {
                return task;
            }
        }
        return null;
    }

    public Tool findToolById(String toolId) {
        for (Tool tool : tools) {
            if (tool.getId().equals(toolId)) {
                return tool;
            }
        }
        return null;
    }

}