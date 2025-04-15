package com.task.taskmanagement.partie1;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static List<Organisation> organisations = new ArrayList<>();
    private static User loggedInUser;

    public static void main(String[] args) {

        initialisationData();
        showLoginMenu();
        scanner.close();
    }

    private static void initialisationData() {
        // Création des organisations
        Organisation organisation1 = new Organisation("1", "Association Verte");
        Organisation organisation2 = new Organisation("2", "Recyclage Montréal");
        organisations.add(organisation1);
        organisations.add(organisation2);

        // Création des administrateur
        Admin admin1 = new Admin("1", "Abass SARR", organisation1);
        Admin admin2 = new Admin("4", "Abdou Karime Diop", organisation1);
        organisation1.addUser(admin1);
        organisation2.addUser(admin2);

        // Création des membres
        Member member1 = new Employee("2", "Mayer Paul", organisation1);
        Member member2 = new Volunteer("3", "Jane Doe", organisation1);
        Member member3 = new Employee("5", "Assane Kamara", organisation2);
        Member member4 = new Volunteer("6", "Akram Memdou", organisation2);
        organisation1.addUser(member1);
        organisation1.addUser(member2);
        organisation2.addUser(member3);
        organisation2.addUser(member4);

        // Créer quelques outils
        Tool tool1 = new ElectricTool("1", "Marteau électrique", true);
        Tool tool2 = new MechanicalTool("2", "Scie à chaîne", true);
        Tool tool3 = new ElectricTool("3", "Perceuse à batterie", true);
        Tool tool4 = new ElectricTool("4", "Perceuse électrique", true);
        Tool tool5 = new MechanicalTool("5", "Marteau", true);
        Tool tool6 = new ElectricTool("6", "Scie circulaire", true);

        organisation1.addTool(tool1);
        organisation1.addTool(tool2);
        organisation1.addTool(tool3);
        organisation2.addTool(tool4);
        organisation2.addTool(tool5);
        organisation2.addTool(tool6);

        // Créer quelques tâches
        Task task1 = new Task("1", "Arrosage", "Arroser les plantes et les arbres du parc", 2, TaskStatus.PLANNED,
                TaskType.BASIC);
        task1.addTool(tool1);
        task1.assignTo(member1);
        member1.addTask(task1);
        organisation1.addTask(task1);

        Task task2 = new Task("2", "Plantation", "Planter de nouveau arbres", 4, TaskStatus.PLANNED, TaskType.BASIC);
        task2.addTool(tool2);
        task2.assignTo(member1);
        member1.addTask(task2);
        organisation1.addTask(task2);

        Task task3 = new Task("3", "Inspection", "Inspecter l'etat des panneaux solaires", 3, TaskStatus.PLANNED,
                TaskType.BASIC);
        task3.addTool(tool3);
        task3.assignTo(member2);
        member2.addTask(task3);
        organisation1.addTask(task3);

        Task task4 = new Task("4", "Nettoyage", "Nettoyer le parc  et rammasserles déchets", 1, TaskStatus.PLANNED,
                TaskType.BASIC);
        task4.addTool(tool4);
        task4.assignTo(member4);
        member4.addTask(task4);
        organisation2.addTask(task4);

        Task task5 = new Task("5", "Installation", "Installer des équipements de recyclage", 6, TaskStatus.PLANNED,
                TaskType.BASIC);
        task5.addTool(tool5);
        task5.assignTo(member3);
        member3.addTask(task5);
        organisation2.addTask(task5);

        Task task6 = new Task("6", "Audit", "Effectuer un audit de recyclage", 5, TaskStatus.PLANNED, TaskType.BASIC);
        task6.addTool(tool6);
        task6.assignTo(member4);
        member4.addTask(task6);
        organisation2.addTask(task6);

    }

    // find organisation by id
    private static Organisation getOrganisationById(String id) {
        for (Organisation organisation : organisations) {
            if (organisation.getId().equals(id)) {
                return organisation;
            }
        }
        System.out.println("ID d'organisation invalide.");
        return null;
    }

    // Methode pour se connecter en tant qu'administrateur
    private static void loginAsAdmin() {
        // Exemple des liste  des organisations et des administrateurs
        System.out.println("Aide memoir: ");
        System.out.println("Nom: Association Verte, ID: 1, Administrateur: Abass SARR, IdAdmin: 1");
        System.out.println("Nom: Recyclage Montréal, ID: 2, Administrateur: Abdou Karime Diop, IdAdmin: 4");
        System.out.println("Entrez l'ID de votre organisation: ");
        String organisationId = scanner.next();

        System.out.print("Entrez votre ID administrateur: ");
        String adminId = scanner.next();

        Organisation organisation = getOrganisationById(organisationId);
        if (organisation == null) {
            return;
        }

        User user = organisation.getUserById(adminId);

        if (user != null && user instanceof Admin) {
            loggedInUser = user;
            System.out.println("Connecté en tant qu'administrateur: " + user.getName());
            showAdminMenu();
        } else {
            System.out.println("ID d'administrateur invalide.");
        }
    }

    // Methode pour se connecter en tant que membre
    private static void loginAsMember() {
        // Exemple des liste  des organisations et des membres
        System.out.println("Aide memoir: ");
        System.out.println("Nom: Association Verte, ID: 1, Membre: Mayer Paul, IdMembre: 2");
        System.out.println("Nom: Recyclage Montréal, ID: 2, Membre: Jane Doe, IdMembre: 3");
        System.out.println("Entrez l'ID de votre organisation: ");
        String organisationId = scanner.next();

        System.out.print("Entrez l'ID du membre: ");
        String memberId = scanner.next();

        Organisation organisation = getOrganisationById(organisationId);
        if (organisation == null) {
            return;
        }

        User user = organisation.getUserById(memberId);

        if (user != null && user instanceof Member) {
            loggedInUser = user;
            System.out.println("Connecté en tant que membre: " + user.getName());
            showMemberMenu();
        } else {
            System.out.println("ID de membre invalide.");
        }
    }

    // recuperer l'entrée de l'utilisateur
    private static int getUserInput() {
        while (!scanner.hasNextInt()) {
            System.out.println("Veuillez entrer un nombre valide.");
            scanner.next();
        }
        int input = scanner.nextInt();
        return input;
    }

    // Menu principale
    private static void showLoginMenu() {
        boolean isUsingApp = false;

        while (!isUsingApp) {
            System.out.println("\n=== Système de Gestion des Tâches ===");
            System.out.println("1. Se connecter en tant qu'administrateur");
            System.out.println("2. Se connecter en tant que membre");
            System.out.println("3. Quitter");
            System.out.print("Choisissez une option: ");

            int choice = getUserInput();

            switch (choice) {
                case 1:
                    loginAsAdmin();
                    break;
                case 2:
                    loginAsMember();
                    break;
                case 3:
                    isUsingApp = true;
                    System.out.println("Au revoir!");
                    break;
                default:
                    System.out.println("Option invalide. Veuillez réessayer.");
            }
        }
    }

    private static void showAdminMenu() {
        Admin admin = (Admin) loggedInUser;
        boolean back = false;

        while (!back) {
            System.out.println("\n=== Menu Administrateur ===");
            System.out.println("1. Afficher les informations de l'organisation");
            System.out.println("2. Rechercher un membre par ID");
            System.out.println("3. Lister les tâches de l'organisation");
            System.out.println("4. Rechercher une tâche par ID");
            System.out.println("5. Rechercher un outil par ID");
            System.out.println("6. Lister tous les outils disponibles");
            System.out.println("7. Retour au menu principal");
            System.out.print("Choisissez une option: ");

            int choice = getUserInput();

            switch (choice) {
                case 1:
                    admin.displayOrganisationInfo();
                    break;
                case 2:
                    System.out.print("Entrez l'ID du membre: ");
                    String memberId = scanner.next();
                    Member member = admin.searchMemberById(memberId);
                    if (member != null) {
                        member.viewPersonalInfo();
                    } else {
                        System.out.println("Membre non trouvé.");
                    }
                    break;
                case 3:
                    List<Task> tasks = admin.listOrganisationTasks();
                    System.out.println("\n=== Tâches de l'organisation ===");
                    for (Task task : tasks) {
                        task.displayInfo();
                        System.out.println("-------------");
                    }
                    break;
                case 4:
                    System.out.print("Entrez l'ID de la tâche: ");
                    String taskId = scanner.next();
                    Task task = admin.searchTaskById(taskId);
                    if (task != null) {
                        task.displayInfo();
                    } else {
                        System.out.println("Tâche non trouvée.");
                    }
                    break;
                case 5:
                    System.out.print("Entrez l'ID de l'outil: ");
                    String toolId = scanner.next();
                    Tool tool = admin.searchToolById(toolId);
                    if (tool != null) {
                        tool.displayInfo();
                    } else {
                        System.out.println("Outil non trouvé.");
                    }
                    break;
                case 6:
                    List<Tool> availableTools = admin.listAvailableTools();
                    System.out.println("\n=== Outils disponibles ===");
                    for (Tool t : availableTools) {
                        t.displayInfo();
                        System.out.println("-------------");
                    }
                    break;
                case 7:
                    back = true;
                    break;
                default:
                    System.out.println("Option invalide. Veuillez réessayer.");
            }
        }
    }

    private static void showMemberMenu() {
        Member member = (Member) loggedInUser;
        boolean back = false;

        while (!back) {
            System.out.println("\n=== Menu Membre ===");
            System.out.println("1. Voir la liste de mes tâches");
            System.out.println("2. Ajouter un outil à une tâche");
            System.out.println("3. Voir les outils utilisés dans une tâche");
            System.out.println("4. Ajouter un commentaire à une tâche");
            System.out.println("5. Voir mes informations et mon score");
            System.out.println("6. Retour au menu principal");
            System.out.print("Choisissez une option: ");

            int choice = getUserInput();

            switch (choice) {
                case 1:
                    member.listTasks();
                    break;
                case 2:
                    addToolToTask(member);
                    break;
                case 3:
                    viewTaskTools(member);
                    break;
                case 4:
                    addCommentToTask(member);
                    break;
                case 5:
                    member.viewPersonalInfo();
                    break;
                case 6:
                    back = true;
                    break;
                default:
                    System.out.println("Option invalide. Veuillez réessayer.");
            }
        }
    }

    private static void addToolToTask(Member member) {
        System.out.println("\n=== Tâches assignées ===");
        List<Task> tasks = member.getTasks();

        if (tasks.isEmpty()) {
            System.out.println("Vous n'avez pas de tâches assignées.");
            return;
        }

        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i).getDescription() + " [" + tasks.get(i).getId() + "]");
        }

        System.out.print("Sélectionnez une tâche (1-" + tasks.size() + "): ");
        int taskIndex = getUserInput() - 1;

        if (taskIndex < 0 || taskIndex >= tasks.size()) {
            System.out.println("Sélection invalide.");
            return;
        }

        Task selectedTask = tasks.get(taskIndex);

        Organisation organisation = member.getOrganisation();

        List<Tool> availableTools = organisation.getAvailableTools();
        System.out.println("\n=== Outils disponibles ===");

        if (availableTools.isEmpty()) {
            System.out.println("Aucun outil disponible.");
            return;
        }

        for (int i = 0; i < availableTools.size(); i++) {
            System.out.println(
                    (i + 1) + ". " + availableTools.get(i).getName() + " [" + availableTools.get(i).getId() + "]");
        }

        System.out.print("Sélectionnez un outil (1-" + availableTools.size() + "): ");
        int toolIndex = getUserInput() - 1;

        if (toolIndex < 0 || toolIndex >= availableTools.size()) {
            System.out.println("Sélection invalide.");
            return;
        }

        Tool selectedTool = availableTools.get(toolIndex);
        member.addToolToTask(selectedTask, selectedTool);
        System.out.println("Outil ajouté à la tâche avec succès.");
    }

    private static void viewTaskTools(Member member) {
        System.out.println("\n=== Tâches assignées ===");
        List<Task> tasks = member.getTasks();

        if (tasks.isEmpty()) {
            System.out.println("Vous n'avez pas de tâches assignées.");
            return;
        }

        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i).getDescription() + " [" + tasks.get(i).getId() + "]");
        }

        System.out.print("Sélectionnez une tâche (1-" + tasks.size() + "): ");
        int taskIndex = getUserInput() - 1;

        if (taskIndex < 0 || taskIndex >= tasks.size()) {
            System.out.println("Sélection invalide.");
            return;
        }

        Task selectedTask = tasks.get(taskIndex);
        member.listTools(selectedTask);
    }

    private static void addCommentToTask(Member member) {
        System.out.println("\n=== Tâches assignées ===");
        List<Task> tasks = member.getTasks();

        if (tasks.isEmpty()) {
            System.out.println("Vous n'avez pas de tâches assignées.");
            return;
        }

        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i).getDescription() + " [" + tasks.get(i).getId() + "]");
        }

        System.out.print("Sélectionnez une tâche (1-" + tasks.size() + "): ");
        int taskIndex = getUserInput() - 1;

        if (taskIndex < 0 || taskIndex >= tasks.size()) {
            System.out.println("Sélection invalide.");
            return;
        }

        Task selectedTask = tasks.get(taskIndex);

        scanner.nextLine();
        System.out.print("Entrez votre commentaire: ");
        String comment = scanner.nextLine();

        member.addCommentToTask(selectedTask, comment);
        System.out.println("Commentaire ajouté avec succès.");
    }

}
