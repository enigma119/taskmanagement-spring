package com.task.taskmanagement.partie1;

//Iniatialisation de la classe Tool
public class Tool {
    private String id;
    private String name;
    private boolean available;

    public Tool(String id, String name, boolean available) {
        this.id = id;
        this.name = name;
        this.available = available;
    }

    // Creation des getters et setters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    // Methode pour afficher les informations de l'outil
    public void displayInfo() {
        System.out.println("ID: " + id);
        System.out.println("Nom: " + name);
        System.out.println("Disponibilité: " + (available ? "Disponible" : "Non disponible"));
        System.out.println("Type: " + (this instanceof ElectricTool ? "Électrique" : "Mécanique"));
    }

}
