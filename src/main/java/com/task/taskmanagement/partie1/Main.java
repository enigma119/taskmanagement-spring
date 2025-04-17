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
        
        // Ajout des scénarios de test pour l'auxiliaire de correction
        createTestScenarios();
        
        showLoginMenu();
        scanner.close();
    }

    private static void initialisationData() {
        // Création des organisations
        Organisation organisation1 = new Organisation("1", "Association Verte");
        Organisation organisation2 = new Organisation("2", "Recyclage Montréal");
        organisations.add(organisation1);
        organisations.add(organisation2);

        // Création des administrateurs
        Admin admin1 = new Admin("1", "Abass SARR", organisation1);
        Admin admin2 = new Admin("4", "Abdou Karime Diop", organisation2);
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

        // Création des outils
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

        // Création des tâches avec les nouveaux types
        Task task1 = new Task("1", "Préparation du site", TaskType.SITE_PREPARATION, false, 8, TaskStatus.PLANNED);
        task1.addTool(tool1);
        task1.assignTo(member1);
        member1.addTask(task1);
        organisation1.addTask(task1);

        // Ajout de sous-tâches
        Task subTask1 = new Task("7", "Nettoyage initial", TaskType.SITE_PREPARATION, false, 2, TaskStatus.PLANNED);
        subTask1.assignTo(member1);
        task1.addSubTask(subTask1);
        member1.addTask(subTask1);
        organisation1.addTask(subTask1);

        Task subTask2 = new Task("8", "Marquage du terrain", TaskType.SITE_PREPARATION, false, 1, TaskStatus.PLANNED);
        subTask2.assignTo(member1);
        task1.addSubTask(subTask2);
        member1.addTask(subTask2);
        organisation1.addTask(subTask2);

        // Plus de tâches principales
        Task task2 = new Task("2", "Fondations", TaskType.FOUNDATION, true, 40, TaskStatus.PLANNED);
        task2.addTool(tool2);
        task2.assignTo(member1);
        member1.addTask(task2);
        organisation1.addTask(task2);

        Task task3 = new Task("3", "Construction de la structure", TaskType.STRUCTURE_CONSTRUCTION, false, 24, TaskStatus.PLANNED);
        task3.addTool(tool3);
        task3.assignTo(member2);
        member2.addTask(task3);
        organisation1.addTask(task3);

        Task task4 = new Task("4", "Toiture et fenêtres", TaskType.PARTITION_AND_ROOFING, true, 48, TaskStatus.PLANNED);
        task4.addTool(tool4);
        task4.assignTo(member4);
        member4.addTask(task4);
        organisation2.addTask(task4);

        Task task5 = new Task("5", "Installation électrique", TaskType.TECHNICAL_INSTALLATION, false, 16, TaskStatus.PLANNED);
        task5.addTool(tool5);
        task5.assignTo(member3);
        member3.addTask(task5);
        organisation2.addTask(task5);

        Task task6 = new Task("6", "Finitions", TaskType.FINISHING_AND_SECURITY, true, 32, TaskStatus.PLANNED);
        task6.addTool(tool6);
        task6.assignTo(member4);
        member4.addTask(task6);
        organisation2.addTask(task6);
    }
    
    private static void createTestScenarios() {
        System.out.println("=== Création des scénarios de test ===");
        
        // Organisation pour les scénarios de test
        Organisation testOrg = new Organisation("TEST", "Organisation de Tests");
        organisations.add(testOrg);
        
        // Administrateur et membres pour les tests
        Admin testAdmin = new Admin("TEST_ADMIN", "Administrateur Test", testOrg);
        testOrg.addUser(testAdmin);
        
        Member testEmployee = new Employee("TEST_EMP", "Employé Test", testOrg);
        Member testVolunteer = new Volunteer("TEST_VOL", "Volontaire Test", testOrg);
        testOrg.addUser(testEmployee);
        testOrg.addUser(testVolunteer);
        
        // Outils pour les tests
        Tool testTool1 = new ElectricTool("TEST_TOOL1", "Perceuse professionnelle", true);
        Tool testTool2 = new MechanicalTool("TEST_TOOL2", "Marteau de charpentier", true);
        testOrg.addTool(testTool1);
        testOrg.addTool(testTool2);
        
        // 1. Scénario: Construction d'un Abri de Jardin (Basic)
        createAbriJardinProject(testOrg, testEmployee, testTool1, testTool2);
        
        // 2. Scénario: Construction d'un Bâtiment Résidentiel (Professional)
        createResidentialBuildingProject(testOrg, testVolunteer, testTool1, testTool2);
        
        // 3. Scénario: Construction d'un Immeuble de Bureaux (Professional)
        createOfficeBuildingProject(testOrg, testEmployee, testTool1, testTool2);
        
        // 4. Scénario: Construction d'un Complexe Hospitalier (Professional)
        createHospitalComplexProject(testOrg, testVolunteer, testTool1, testTool2);
        
        System.out.println("Scénarios de test créés avec succès.");
        System.out.println("Utilisez les identifiants suivants pour tester:");
        System.out.println("Organisation: TEST");
        System.out.println("Admin: TEST_ADMIN");
        System.out.println("Employé: TEST_EMP");
        System.out.println("Volontaire: TEST_VOL");
        System.out.println("===========================================");
    }
    
    private static void createAbriJardinProject(Organisation org, Member assignee, Tool tool1, Tool tool2) {
        // Tâche principale: Préparation du site
        Task preparationSite = new Task("ABRI_1", "Préparation du Site", TaskType.SITE_PREPARATION, false, 8, TaskStatus.PLANNED);
        preparationSite.assignTo(assignee);
        assignee.addTask(preparationSite);
        org.addTask(preparationSite);
        
        // Sous-tâches: Études et analyses
        Task etudesAnalyses = new Task("ABRI_1_1", "Études et analyses", TaskType.SITE_PREPARATION, false, 2, TaskStatus.PLANNED);
        etudesAnalyses.assignTo(assignee);
        preparationSite.addSubTask(etudesAnalyses);
        assignee.addTask(etudesAnalyses);
        org.addTask(etudesAnalyses);
        
        Task verificationSol = new Task("ABRI_1_1_1", "Vérification du sol", TaskType.SITE_PREPARATION, false, 1, TaskStatus.PLANNED);
        verificationSol.assignTo(assignee);
        etudesAnalyses.addSubTask(verificationSol);
        assignee.addTask(verificationSol);
        org.addTask(verificationSol);
        
        Task obtentionAutorisations = new Task("ABRI_1_1_2", "Obtention des autorisations", TaskType.SITE_PREPARATION, false, 1, TaskStatus.PLANNED);
        obtentionAutorisations.assignTo(assignee);
        etudesAnalyses.addSubTask(obtentionAutorisations);
        assignee.addTask(obtentionAutorisations);
        org.addTask(obtentionAutorisations);
        
        // Sous-tâches: Nettoyage et déblayage
        Task nettoyageDeblayage = new Task("ABRI_1_2", "Nettoyage et déblayage", TaskType.SITE_PREPARATION, false, 2, TaskStatus.PLANNED);
        nettoyageDeblayage.assignTo(assignee);
        preparationSite.addSubTask(nettoyageDeblayage);
        assignee.addTask(nettoyageDeblayage);
        org.addTask(nettoyageDeblayage);
        
        Task debroussaillage = new Task("ABRI_1_2_1", "Débroussaillage", TaskType.SITE_PREPARATION, false, 1, TaskStatus.PLANNED);
        debroussaillage.assignTo(assignee);
        nettoyageDeblayage.addSubTask(debroussaillage);
        assignee.addTask(debroussaillage);
        org.addTask(debroussaillage);
        
        Task evacuationDechets = new Task("ABRI_1_2_2", "Évacuation des déchets", TaskType.SITE_PREPARATION, false, 1, TaskStatus.PLANNED);
        evacuationDechets.assignTo(assignee);
        nettoyageDeblayage.addSubTask(evacuationDechets);
        assignee.addTask(evacuationDechets);
        org.addTask(evacuationDechets);
        
        // Tâche principale: Fondations
        Task fondations = new Task("ABRI_2", "Fondations", TaskType.FOUNDATION, false, 10, TaskStatus.PLANNED);
        fondations.assignTo(assignee);
        assignee.addTask(fondations);
        org.addTask(fondations);
        
        // Sous-tâches: Excavation
        Task excavation = new Task("ABRI_2_1", "Excavation", TaskType.FOUNDATION, false, 4, TaskStatus.PLANNED);
        excavation.assignTo(assignee);
        fondations.addSubTask(excavation);
        assignee.addTask(excavation);
        org.addTask(excavation);
        
        Task creusementManuel = new Task("ABRI_2_1_1", "Creusement manuel", TaskType.FOUNDATION, false, 2, TaskStatus.PLANNED);
        creusementManuel.assignTo(assignee);
        excavation.addSubTask(creusementManuel);
        assignee.addTask(creusementManuel);
        org.addTask(creusementManuel);
        
        Task miseEnPlaceNivellement = new Task("ABRI_2_1_2", "Mise en place du nivellement", TaskType.FOUNDATION, false, 2, TaskStatus.PLANNED);
        miseEnPlaceNivellement.assignTo(assignee);
        excavation.addSubTask(miseEnPlaceNivellement);
        assignee.addTask(miseEnPlaceNivellement);
        org.addTask(miseEnPlaceNivellement);
        
        // Sous-tâches: Mise en place des armatures et béton
        Task miseEnPlaceArmatures = new Task("ABRI_2_2", "Mise en place des armatures et béton", TaskType.FOUNDATION, false, 6, TaskStatus.PLANNED);
        miseEnPlaceArmatures.assignTo(assignee);
        fondations.addSubTask(miseEnPlaceArmatures);
        assignee.addTask(miseEnPlaceArmatures);
        org.addTask(miseEnPlaceArmatures);
        
        Task poseCoffrage = new Task("ABRI_2_2_1", "Pose du coffrage", TaskType.FOUNDATION, false, 2, TaskStatus.PLANNED);
        poseCoffrage.assignTo(assignee);
        miseEnPlaceArmatures.addSubTask(poseCoffrage);
        assignee.addTask(poseCoffrage);
        org.addTask(poseCoffrage);
        
        Task coulageBeton = new Task("ABRI_2_2_2", "Coulage du béton", TaskType.FOUNDATION, false, 2, TaskStatus.PLANNED);
        coulageBeton.assignTo(assignee);
        miseEnPlaceArmatures.addSubTask(coulageBeton);
        assignee.addTask(coulageBeton);
        org.addTask(coulageBeton);
        
        Task sechageDurcissement = new Task("ABRI_2_2_3", "Séchage et durcissement", TaskType.FOUNDATION, false, 2, TaskStatus.PLANNED);
        sechageDurcissement.assignTo(assignee);
        miseEnPlaceArmatures.addSubTask(sechageDurcissement);
        assignee.addTask(sechageDurcissement);
        org.addTask(sechageDurcissement);
        
        // Tâche principale: Construction de la Structure
        Task constructionStructure = new Task("ABRI_3", "Construction de la Structure", TaskType.STRUCTURE_CONSTRUCTION, false, 12, TaskStatus.PLANNED);
        constructionStructure.assignTo(assignee);
        assignee.addTask(constructionStructure);
        org.addTask(constructionStructure);
        
        // Sous-tâches: Construction des murs et du toit
        Task constructionMursToit = new Task("ABRI_3_1", "Construction des murs et du toit", TaskType.STRUCTURE_CONSTRUCTION, false, 12, TaskStatus.PLANNED);
        constructionMursToit.assignTo(assignee);
        constructionStructure.addSubTask(constructionMursToit);
        assignee.addTask(constructionMursToit);
        org.addTask(constructionMursToit);
        
        Task montageStructure = new Task("ABRI_3_1_1", "Montage de la structure en bois/métal", TaskType.STRUCTURE_CONSTRUCTION, false, 4, TaskStatus.PLANNED);
        montageStructure.assignTo(assignee);
        constructionMursToit.addSubTask(montageStructure);
        assignee.addTask(montageStructure);
        org.addTask(montageStructure);
        
        Task fixationPanneaux = new Task("ABRI_3_1_2", "Fixation des panneaux latéraux", TaskType.STRUCTURE_CONSTRUCTION, false, 4, TaskStatus.PLANNED);
        fixationPanneaux.assignTo(assignee);
        constructionMursToit.addSubTask(fixationPanneaux);
        assignee.addTask(fixationPanneaux);
        org.addTask(fixationPanneaux);
        
        Task installationToiture = new Task("ABRI_3_1_3", "Installation de la toiture", TaskType.STRUCTURE_CONSTRUCTION, false, 4, TaskStatus.PLANNED);
        installationToiture.assignTo(assignee);
        constructionMursToit.addSubTask(installationToiture);
        assignee.addTask(installationToiture);
        org.addTask(installationToiture);
        
        // Tâche principale: Finitions et Sécurisation
        Task finitionsSecurisation = new Task("ABRI_4", "Finitions et Sécurisation", TaskType.FINISHING_AND_SECURITY, false, 8, TaskStatus.PLANNED);
        finitionsSecurisation.assignTo(assignee);
        assignee.addTask(finitionsSecurisation);
        org.addTask(finitionsSecurisation);
        
        // Sous-tâches: Peinture et revêtements
        Task peintureRevetements = new Task("ABRI_4_1", "Peinture et revêtements", TaskType.FINISHING_AND_SECURITY, false, 4, TaskStatus.PLANNED);
        peintureRevetements.assignTo(assignee);
        finitionsSecurisation.addSubTask(peintureRevetements);
        assignee.addTask(peintureRevetements);
        org.addTask(peintureRevetements);
        
        Task applicationAppret = new Task("ABRI_4_1_1", "Application de l'apprêt", TaskType.FINISHING_AND_SECURITY, false, 2, TaskStatus.PLANNED);
        applicationAppret.assignTo(assignee);
        peintureRevetements.addSubTask(applicationAppret);
        assignee.addTask(applicationAppret);
        org.addTask(applicationAppret);
        
        Task peintureVernis = new Task("ABRI_4_1_2", "Peinture et vernis", TaskType.FINISHING_AND_SECURITY, false, 2, TaskStatus.PLANNED);
        peintureVernis.assignTo(assignee);
        peintureRevetements.addSubTask(peintureVernis);
        assignee.addTask(peintureVernis);
        org.addTask(peintureVernis);
        
        // Sous-tâches: Sécurisation de la structure
        Task securisationStructure = new Task("ABRI_4_2", "Sécurisation de la structure", TaskType.FINISHING_AND_SECURITY, false, 4, TaskStatus.PLANNED);
        securisationStructure.assignTo(assignee);
        finitionsSecurisation.addSubTask(securisationStructure);
        assignee.addTask(securisationStructure);
        org.addTask(securisationStructure);
        
        Task installationRenforts = new Task("ABRI_4_2_1", "Installation des renforts", TaskType.FINISHING_AND_SECURITY, false, 2, TaskStatus.PLANNED);
        installationRenforts.assignTo(assignee);
        securisationStructure.addSubTask(installationRenforts);
        assignee.addTask(installationRenforts);
        org.addTask(installationRenforts);
        
        Task verificationFixations = new Task("ABRI_4_2_2", "Vérification des fixations", TaskType.FINISHING_AND_SECURITY, false, 2, TaskStatus.PLANNED);
        verificationFixations.assignTo(assignee);
        securisationStructure.addSubTask(verificationFixations);
        assignee.addTask(verificationFixations);
        org.addTask(verificationFixations);
        
        // Ajout des outils aux tâches principales
        preparationSite.addTool(tool1);
        fondations.addTool(tool2);
        constructionStructure.addTool(tool1);
        finitionsSecurisation.addTool(tool2);
    }
    
    private static void createResidentialBuildingProject(Organisation org, Member assignee, Tool tool1, Tool tool2) {
        // Implémentation du deuxième scénario: Construction d'un Bâtiment Résidentiel (1 étage)
        // Seules les tâches principales et quelques sous-tâches sont implémentées pour garder l'exemple concis
        
        // Tâche principale: Préparation du Site
        Task preparationSite = new Task("RES_1", "Préparation du Site", TaskType.SITE_PREPARATION, true, 16, TaskStatus.PLANNED);
        preparationSite.assignTo(assignee);
        assignee.addTask(preparationSite);
        org.addTask(preparationSite);
        
        // Sous-tâches de niveau 1
        Task etudesAnalyses = new Task("RES_1_1", "Études et analyses", TaskType.SITE_PREPARATION, true, 6, TaskStatus.PLANNED);
        etudesAnalyses.assignTo(assignee);
        preparationSite.addSubTask(etudesAnalyses);
        assignee.addTask(etudesAnalyses);
        org.addTask(etudesAnalyses);
        
        Task nettoyageDeblayage = new Task("RES_1_2", "Nettoyage et déblayage", TaskType.SITE_PREPARATION, true, 6, TaskStatus.PLANNED);
        nettoyageDeblayage.assignTo(assignee);
        preparationSite.addSubTask(nettoyageDeblayage);
        assignee.addTask(nettoyageDeblayage);
        org.addTask(nettoyageDeblayage);
        
        Task marquageDelimitation = new Task("RES_1_3", "Marquage et délimitation", TaskType.SITE_PREPARATION, true, 4, TaskStatus.PLANNED);
        marquageDelimitation.assignTo(assignee);
        preparationSite.addSubTask(marquageDelimitation);
        assignee.addTask(marquageDelimitation);
        org.addTask(marquageDelimitation);
        
        // Tâche principale: Fondations
        Task fondations = new Task("RES_2", "Fondations", TaskType.FOUNDATION, true, 30, TaskStatus.PLANNED);
        fondations.assignTo(assignee);
        assignee.addTask(fondations);
        org.addTask(fondations);
        
        // Tâche principale: Construction de la Structure
        Task constructionStructure = new Task("RES_3", "Construction de la Structure", TaskType.STRUCTURE_CONSTRUCTION, true, 40, TaskStatus.PLANNED);
        constructionStructure.assignTo(assignee);
        assignee.addTask(constructionStructure);
        org.addTask(constructionStructure);
        
        // Tâche principale: Installation des Systèmes Techniques
        Task installationSystemes = new Task("RES_4", "Installation des Systèmes Techniques", TaskType.TECHNICAL_INSTALLATION, true, 30, TaskStatus.PLANNED);
        installationSystemes.assignTo(assignee);
        assignee.addTask(installationSystemes);
        org.addTask(installationSystemes);
        
        // Tâche principale: Finitions et Sécurisation
        Task finitionsSecurisation = new Task("RES_5", "Finitions et Sécurisation", TaskType.FINISHING_AND_SECURITY, true, 22, TaskStatus.PLANNED);
        finitionsSecurisation.assignTo(assignee);
        assignee.addTask(finitionsSecurisation);
        org.addTask(finitionsSecurisation);
        
        // Tâche principale: Nettoyage et Réception
        Task nettoyageReception = new Task("RES_6", "Nettoyage et Réception", TaskType.CLEANING_AND_INSPECTION, true, 15, TaskStatus.PLANNED);
        nettoyageReception.assignTo(assignee);
        assignee.addTask(nettoyageReception);
        org.addTask(nettoyageReception);
        
        // Ajout des outils aux tâches principales
        preparationSite.addTool(tool1);
        fondations.addTool(tool2);
        constructionStructure.addTool(tool1);
        installationSystemes.addTool(tool2);
        finitionsSecurisation.addTool(tool1);
        nettoyageReception.addTool(tool2);
    }
    
    private static void createOfficeBuildingProject(Organisation org, Member assignee, Tool tool1, Tool tool2) {
        // Implémentation du troisième scénario: Construction d'un Immeuble de Bureaux (5 étages)
        // Seules les tâches principales sont implémentées pour garder l'exemple concis
        
        // Tâche principale: Préparation du Site
        Task preparationSite = new Task("BUR_1", "Préparation du Site (Immeuble de Bureaux)", TaskType.SITE_PREPARATION, true, 18, TaskStatus.PLANNED);
        preparationSite.assignTo(assignee);
        assignee.addTask(preparationSite);
        org.addTask(preparationSite);
        
        // Tâche principale: Fondations
        Task fondations = new Task("BUR_2", "Fondations (Immeuble de Bureaux)", TaskType.FOUNDATION, true, 30, TaskStatus.PLANNED);
        fondations.assignTo(assignee);
        assignee.addTask(fondations);
        org.addTask(fondations);
        
        // Tâche principale: Construction de la Structure
        Task constructionStructure = new Task("BUR_3", "Construction de la Structure (Immeuble de Bureaux)", TaskType.STRUCTURE_CONSTRUCTION, true, 40, TaskStatus.PLANNED);
        constructionStructure.assignTo(assignee);
        assignee.addTask(constructionStructure);
        org.addTask(constructionStructure);
        
        // Tâche principale: Installation des Systèmes Techniques
        Task installationSystemes = new Task("BUR_4", "Installation des Systèmes Techniques (Immeuble de Bureaux)", TaskType.TECHNICAL_INSTALLATION, true, 30, TaskStatus.PLANNED);
        installationSystemes.assignTo(assignee);
        assignee.addTask(installationSystemes);
        org.addTask(installationSystemes);
        
        // Ajout des outils aux tâches principales
        preparationSite.addTool(tool1);
        fondations.addTool(tool2);
        constructionStructure.addTool(tool1);
        installationSystemes.addTool(tool2);
    }
    
    private static void createHospitalComplexProject(Organisation org, Member assignee, Tool tool1, Tool tool2) {
        // Implémentation du quatrième scénario: Construction d'un Complexe Hospitalier
        // Seules les tâches principales sont implémentées pour garder l'exemple concis
        
        // Tâche principale: Préparation du Site
        Task preparationSite = new Task("HOP_1", "Préparation du Site (Complexe Hospitalier)", TaskType.SITE_PREPARATION, true, 18, TaskStatus.PLANNED);
        preparationSite.assignTo(assignee);
        assignee.addTask(preparationSite);
        org.addTask(preparationSite);
        
        // Tâche principale: Fondations
        Task fondations = new Task("HOP_2", "Fondations (Complexe Hospitalier)", TaskType.FOUNDATION, true, 30, TaskStatus.PLANNED);
        fondations.assignTo(assignee);
        assignee.addTask(fondations);
        org.addTask(fondations);
        
        // Tâche principale: Construction de la Structure
        Task constructionStructure = new Task("HOP_3", "Construction de la Structure (Complexe Hospitalier)", TaskType.STRUCTURE_CONSTRUCTION, true, 40, TaskStatus.PLANNED);
        constructionStructure.assignTo(assignee);
        assignee.addTask(constructionStructure);
        org.addTask(constructionStructure);
        
        // Tâche principale: Installation des Systèmes Techniques
        Task installationSystemes = new Task("HOP_4", "Installation des Systèmes Techniques (Complexe Hospitalier)", TaskType.TECHNICAL_INSTALLATION, true, 30, TaskStatus.PLANNED);
        installationSystemes.assignTo(assignee);
        assignee.addTask(installationSystemes);
        org.addTask(installationSystemes);
        
        // Tâche principale: Finitions et Sécurisation
        Task finitionsSecurisation = new Task("HOP_5", "Finitions et Sécurisation (Complexe Hospitalier)", TaskType.FINISHING_AND_SECURITY, true, 22, TaskStatus.PLANNED);
        finitionsSecurisation.assignTo(assignee);
        assignee.addTask(finitionsSecurisation);
        org.addTask(finitionsSecurisation);
        
        // Tâche principale: Nettoyage et Réception
        Task nettoyageReception = new Task("HOP_6", "Nettoyage et Réception (Complexe Hospitalier)", TaskType.CLEANING_AND_INSPECTION, true, 15, TaskStatus.PLANNED);
        nettoyageReception.assignTo(assignee);
        assignee.addTask(nettoyageReception);
        org.addTask(nettoyageReception);
        
        // Ajout des outils aux tâches principales
        preparationSite.addTool(tool1);
        fondations.addTool(tool2);
        constructionStructure.addTool(tool1);
        installationSystemes.addTool(tool2);
        finitionsSecurisation.addTool(tool1);
        nettoyageReception.addTool(tool2);
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
        System.out.println("Nom: Organisation de Tests, ID: TEST, Administrateur: Administrateur Test, IdAdmin: TEST_ADMIN");
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
        System.out.println("Nom: Association Verte, ID: 1, Membre: Jane Doe, IdMembre: 3");
        System.out.println("Nom: Recyclage Montréal, ID: 2, Membre: Assane Kamara, IdMembre: 5");
        System.out.println("Nom: Recyclage Montréal, ID: 2, Membre: Akram Memdou, IdMembre: 6");
        System.out.println("Nom: Organisation de Tests, ID: TEST, Membre: Employé Test, IdMembre: TEST_EMP");
        System.out.println("Nom: Organisation de Tests, ID: TEST, Membre: Volontaire Test, IdMembre: TEST_VOL");
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
            System.out.println("7. Voir le progrès d'une tâche");
            System.out.println("8. Ajouter une sous-tâche");
            System.out.println("9. Retour au menu principal");
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
                    viewTaskProgressAdmin(admin);
                    break;
                case 8:
                    addSubTaskAdmin(admin);
                    break;
                case 9:
                    back = true;
                    break;
                default:
                    System.out.println("Option invalide. Veuillez réessayer.");
            }
        }
    }

    private static void viewTaskProgressAdmin(Admin admin) {
        System.out.print("Entrez l'ID de la tâche: ");
        String taskId = scanner.next();
        Task task = admin.searchTaskById(taskId);
        if (task != null) {
            admin.viewTaskProgress(task);
        } else {
            System.out.println("Tâche non trouvée.");
        }
    }

    private static void addSubTaskAdmin(Admin admin) {
        System.out.print("Entrez l'ID de la tâche parente: ");
        String taskId = scanner.next();
        Task parentTask = admin.searchTaskById(taskId);
        
        if (parentTask == null) {
            System.out.println("Tâche parente non trouvée.");
            return;
        }
        
        scanner.nextLine(); // Consommer la ligne restante
        System.out.print("Description de la sous-tâche: ");
        String description = scanner.nextLine();
        
        System.out.print("Durée estimée (en heures): ");
        int duration = getUserInput();
        
        admin.addSubTaskToTask(parentTask, description, duration);
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
            System.out.println("6. Voir le progrès d'une tâche");
            System.out.println("7. Compléter une tâche");
            System.out.println("8. Ajouter une sous-tâche");
            System.out.println("9. Retour au menu principal");
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
                    viewTaskProgress(member);
                    break;
                case 7:
                    completeTask(member);
                    break;
                case 8:
                    addSubTask(member);
                    break;
                case 9:
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


    // Nouveau methodes
    private static void viewTaskProgress(Member member) {
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
        member.viewTaskProgress(selectedTask);
    }

    private static void completeTask(Member member) {
        System.out.println("\n=== Tâches assignées ===");
        List<Task> tasks = member.getTasks();

        if (tasks.isEmpty()) {
            System.out.println("Vous n'avez pas de tâches assignées.");
            return;
        }

        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i).getDescription() + " [" + tasks.get(i).getId() + "]");
        }

        System.out.print("Sélectionnez une tâche à compléter (1-" + tasks.size() + "): ");
        int taskIndex = getUserInput() - 1;

        if (taskIndex < 0 || taskIndex >= tasks.size()) {
            System.out.println("Sélection invalide.");
            return;
        }

        Task selectedTask = tasks.get(taskIndex);
        member.completeTask(selectedTask);
        System.out.println("Tâche marquée comme terminée. Vous avez gagné " + selectedTask.calculateTotalPoints() + " points.");
    }

    private static void addSubTask(Member member) {
        System.out.println("\n=== Tâches assignées ===");
        List<Task> tasks = member.getTasks();

        if (tasks.isEmpty()) {
            System.out.println("Vous n'avez pas de tâches assignées.");
            return;
        }

        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i).getDescription() + " [" + tasks.get(i).getId() + "]");
        }

        System.out.print("Sélectionnez une tâche parente (1-" + tasks.size() + "): ");
        int taskIndex = getUserInput() - 1;

        if (taskIndex < 0 || taskIndex >= tasks.size()) {
            System.out.println("Sélection invalide.");
            return;
        }

        Task parentTask = tasks.get(taskIndex);
        
        scanner.nextLine(); // Consommer la ligne restante
        System.out.print("Description de la sous-tâche: ");
        String description = scanner.nextLine();
        
        System.out.print("Durée estimée (en heures): ");
        int duration = getUserInput();
        
        member.addSubTask(parentTask, description, duration);
        System.out.println("Sous-tâche ajoutée avec succès.");
    }

}
