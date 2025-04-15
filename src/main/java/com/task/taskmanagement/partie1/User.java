package com.task.taskmanagement.partie1;

public abstract class User {
    protected String id;
    protected String name;
    protected Organisation organisation;

    public User(String id, String name, Organisation organisation) {
        this.id = id;
        this.name = name;
        this.organisation = organisation;
    }

    //  Mise en place des getters et setters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Organisation getOrganisation() {
        return organisation;
    }

    // Methode pour afficher les informations de l'utilisateur
    public void displayInfo() {
        System.out.println("ID: " + id);
        System.out.println("Nom: " + name);
    }

}
