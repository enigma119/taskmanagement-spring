package com.task.taskmanagement.config;

import com.task.taskmanagement.model.*;
import com.task.taskmanagement.model.enums.*;
import com.task.taskmanagement.model.tasks.*;
import com.task.taskmanagement.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class MongoDataInitializer {

    @Bean
    public CommandLineRunner initMongo(
            OrganisationRepository organisationRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            ToolRepository toolRepository,
            TaskRepository taskRepository) {
        
        return args -> {
            // Vérifier si des données existent déjà
            if (organisationRepository.count() > 0) {
                return; // Ne pas réinitialiser les données
            }
            
            // Création des organisations
            Organisation organisation1 = new Organisation();
            organisation1.setName("Association Verte");
            organisation1 = organisationRepository.save(organisation1);

            Organisation organisation2 = new Organisation();
            organisation2.setName("Recyclage Montréal");
            organisation2 = organisationRepository.save(organisation2);

            // Création des admins
            Admin admin1 = Admin.builder()
                    .username("admin1")
                    .password(passwordEncoder.encode("Password123"))
                    .name("Abass SARR")
                    .email("abass.sarr@gmail.com")
                    .organisationId(organisation1.getId())
                    .role("ROLE_ADMIN")
                    .build();
            admin1 = (Admin) userRepository.save(admin1);

            Admin admin2 = Admin.builder()
                    .username("admin2")
                    .password(passwordEncoder.encode("Password123"))
                    .name("Abdou Karime Diop")
                    .email("abdou.diop@gmail.com")
                    .organisationId(organisation2.getId())
                    .role("ROLE_ADMIN")
                    .build();
            admin2 = (Admin) userRepository.save(admin2);

            // Création des membres
            Employee employee1 = Employee.builder()
                    .username("employee1")
                    .password(passwordEncoder.encode("Password123"))
                    .name("Mayer Paul")
                    .email("mayer.paul@gmail.com")
                    .organisationId(organisation1.getId())
                    .role("ROLE_MEMBER")
                    .score(0)
                    .build();
            employee1 = (Employee) userRepository.save(employee1);

            Volunteer volunteer1 = Volunteer.builder()
                    .username("volunteer1")
                    .password(passwordEncoder.encode("Password123"))
                    .name("Jane Doe")
                    .email("jane.doe@gmail.com")
                    .organisationId(organisation1.getId())
                    .role("ROLE_MEMBER")
                    .score(0)
                    .build();
            volunteer1 = (Volunteer) userRepository.save(volunteer1);

            Employee employee2 = Employee.builder()
                    .username("employee2")
                    .password(passwordEncoder.encode("Password123"))
                    .name("Assane Kamara")
                    .email("assane.kamara@gmail.com")
                    .organisationId(organisation2.getId())
                    .role("ROLE_MEMBER")
                    .score(0)
                    .build();
            employee2 = (Employee) userRepository.save(employee2);

            Volunteer volunteer2 = Volunteer.builder()
                    .username("volunteer2")
                    .password(passwordEncoder.encode("Password123"))
                    .name("Akram Memdou")
                    .email("akram.memdou@gmail.com")
                    .organisationId(organisation2.getId())
                    .role("ROLE_MEMBER")
                    .score(0)
                    .build();
            volunteer2 = (Volunteer) userRepository.save(volunteer2);

            // Création des outils
            Tool tool1 = ElectricTool.builder()
                    .name("Marteau électrique")
                    .available(true)
                    .organisationId(organisation1.getId())
                    .build();
            tool1 = toolRepository.save(tool1);

            Tool tool2 = MechanicalTool.builder()
                    .name("Scie à chaîne")
                    .available(true)
                    .organisationId(organisation1.getId())
                    .build();
            tool2 = toolRepository.save(tool2);

            Tool tool3 = ElectricTool.builder()
                    .name("Perceuse à batterie")
                    .available(true)
                    .organisationId(organisation1.getId())
                    .build();
            tool3 = toolRepository.save(tool3);

            Tool tool4 = ElectricTool.builder()
                    .name("Perceuse électrique")
                    .available(true)
                    .organisationId(organisation2.getId())
                    .build();
            tool4 = toolRepository.save(tool4);

            Tool tool5 = MechanicalTool.builder()
                    .name("Marteau")
                    .available(true)
                    .organisationId(organisation2.getId())
                    .build();
            tool5 = toolRepository.save(tool5);

            Tool tool6 = ElectricTool.builder()
                    .name("Scie circulaire")
                    .available(true)
                    .organisationId(organisation2.getId())
                    .build();
            tool6 = toolRepository.save(tool6);

            // Création des tâches avec les nouveaux types
            SitePreparationTask task1 = new SitePreparationTask();
            task1.setDescription("Préparation du site");
            task1.setCategory(TaskCategory.BASIC);
            task1.setStatus(TaskStatus.PLANNED);
            task1.setComment("Arroser les plantes et les arbres du parc");
            task1.setAssignedMemberId(employee1.getId());
            task1.setOrganisationId(organisation1.getId());
            task1.addTool(tool1);
            task1 = (SitePreparationTask) taskRepository.save(task1);

            // Ajout de sous-tâches
            Task subTask1 = new Task();
            subTask1.setDescription("Nettoyage initial");
            subTask1.setType(TaskType.SITE_PREPARATION);
            subTask1.setCategory(TaskCategory.BASIC);
            subTask1.setStatus(TaskStatus.PLANNED);
            subTask1.setParentTaskId(task1.getId());
            subTask1.setAssignedMemberId(employee1.getId());
            subTask1.setOrganisationId(organisation1.getId());
            subTask1 = taskRepository.save(subTask1);

            Task subTask2 = new Task();
            subTask2.setDescription("Marquage du terrain");
            subTask2.setType(TaskType.SITE_PREPARATION);
            subTask2.setCategory(TaskCategory.BASIC);
            subTask2.setStatus(TaskStatus.PLANNED);
            subTask2.setParentTaskId(task1.getId());
            subTask2.setAssignedMemberId(employee1.getId());
            subTask2.setOrganisationId(organisation1.getId());
            subTask2 = taskRepository.save(subTask2);

            // Création d'autres tâches principales
            FoundationTask task2 = new FoundationTask();
            task2.setDescription("Fondations");
            task2.setCategory(TaskCategory.PROFESSIONAL);
            task2.setStatus(TaskStatus.PLANNED);
            task2.setComment("Construire les fondations");
            task2.setAssignedMemberId(volunteer1.getId());
            task2.setOrganisationId(organisation1.getId());
            task2.addTool(tool2);
            taskRepository.save(task2);

            StructureConstructionTask task3 = new StructureConstructionTask();
            task3.setDescription("Construction de la structure");
            task3.setCategory(TaskCategory.PROFESSIONAL);
            task3.setStatus(TaskStatus.PLANNED);
            task3.setAssignedMemberId(volunteer1.getId());
            task3.setOrganisationId(organisation1.getId());
            task3.addTool(tool3);
            taskRepository.save(task3);

            PartitionAndRoofingTask task4 = new PartitionAndRoofingTask();
            task4.setDescription("Toiture et fenêtres");
            task4.setCategory(TaskCategory.PROFESSIONAL);
            task4.setStatus(TaskStatus.PLANNED);
            task4.setAssignedMemberId(volunteer2.getId());
            task4.setOrganisationId(organisation2.getId());
            task4.addTool(tool4);
            taskRepository.save(task4);

            TechnicalInstallationTask task5 = new TechnicalInstallationTask();
            task5.setDescription("Installation électrique");
            task5.setCategory(TaskCategory.PROFESSIONAL);
            task5.setStatus(TaskStatus.PLANNED);
            task5.setAssignedMemberId(employee2.getId());
            task5.setOrganisationId(organisation2.getId());
            task5.addTool(tool5);
            taskRepository.save(task5);

            FinishingAndSecurityTask task6 = new FinishingAndSecurityTask();
            task6.setDescription("Finitions");
            task6.setCategory(TaskCategory.PROFESSIONAL);
            task6.setStatus(TaskStatus.PLANNED);
            task6.setAssignedMemberId(volunteer2.getId());
            task6.setOrganisationId(organisation2.getId());
            task6.addTool(tool6);
            taskRepository.save(task6);

            // Création des scénarios de test
            createTestScenarios(organisationRepository, userRepository, passwordEncoder, toolRepository, taskRepository);
        };
    }

    private void createTestScenarios(
            OrganisationRepository organisationRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            ToolRepository toolRepository,
            TaskRepository taskRepository) {
        
        // Organisation pour les scénarios de test
        Organisation testOrg = new Organisation();
        testOrg.setName("Organisation de Tests");
        testOrg = organisationRepository.save(testOrg);
        
        // Administrateur et membres pour les tests
        Admin testAdmin = Admin.builder()
                .username("TEST_ADMIN")
                .password(passwordEncoder.encode("Password123"))
                .name("Administrateur Test")
                .email("admin.test@gmail.com")
                .organisationId(testOrg.getId())
                .role("ROLE_ADMIN")
                .build();
        testAdmin = (Admin) userRepository.save(testAdmin);
        
        Employee testEmployee = Employee.builder()
                .username("TEST_EMP")
                .password(passwordEncoder.encode("Password123"))
                .name("Employé Test")
                .email("employee.test@gmail.com")
                .organisationId(testOrg.getId())
                .role("ROLE_MEMBER")
                .score(0)
                .build();
        testEmployee = (Employee) userRepository.save(testEmployee);
        
        Volunteer testVolunteer = Volunteer.builder()
                .username("TEST_VOL")
                .password(passwordEncoder.encode("Password123"))
                .name("Volontaire Test")
                .email("volunteer.test@gmail.com")
                .organisationId(testOrg.getId())
                .role("ROLE_MEMBER")
                .score(0)
                .build();
        testVolunteer = (Volunteer) userRepository.save(testVolunteer);
        
        // Outils pour les tests
        Tool testTool1 = ElectricTool.builder()
                .name("Perceuse professionnelle")
                .available(true)
                .organisationId(testOrg.getId())
                .build();
        testTool1 = toolRepository.save(testTool1);
        
        Tool testTool2 = MechanicalTool.builder()
                .name("Marteau de charpentier")
                .available(true)
                .organisationId(testOrg.getId())
                .build();
        testTool2 = toolRepository.save(testTool2);
        
        // Création des scénarios de test
        createAbriJardinProject(testOrg, testEmployee, testTool1, testTool2, taskRepository);
        createResidentialBuildingProject(testOrg, testVolunteer, testTool1, testTool2, taskRepository);
        createOfficeBuildingProject(testOrg, testEmployee, testTool1, testTool2, taskRepository);
        createHospitalComplexProject(testOrg, testVolunteer, testTool1, testTool2, taskRepository);
    }

    private void createAbriJardinProject(Organisation org, Member assignee, Tool tool1, Tool tool2, TaskRepository taskRepository) {
        // Tâche principale: Préparation du site
        Task preparationSite = new Task();
        preparationSite.setDescription("Préparation du Site");
        preparationSite.setType(TaskType.SITE_PREPARATION);
        preparationSite.setCategory(TaskCategory.BASIC);
        preparationSite.setStatus(TaskStatus.PLANNED);
        preparationSite.setAssignedMemberId(assignee.getId());
        preparationSite.setOrganisationId(org.getId());
        preparationSite.addTool(tool1);
        preparationSite = taskRepository.save(preparationSite);
        
        // Sous-tâches: Études et analyses
        Task etudesAnalyses = new Task();
        etudesAnalyses.setDescription("Études et analyses");
        etudesAnalyses.setType(TaskType.SITE_PREPARATION);
        etudesAnalyses.setCategory(TaskCategory.BASIC);
        etudesAnalyses.setStatus(TaskStatus.PLANNED);
        etudesAnalyses.setParentTaskId(preparationSite.getId());
        etudesAnalyses.setAssignedMemberId(assignee.getId());
        etudesAnalyses.setOrganisationId(org.getId());
        etudesAnalyses = taskRepository.save(etudesAnalyses);
        
        Task verificationSol = new Task();
        verificationSol.setDescription("Vérification du sol");
        verificationSol.setType(TaskType.SITE_PREPARATION);
        verificationSol.setCategory(TaskCategory.BASIC);
        verificationSol.setStatus(TaskStatus.PLANNED);
        verificationSol.setParentTaskId(etudesAnalyses.getId());
        verificationSol.setAssignedMemberId(assignee.getId());
        verificationSol.setOrganisationId(org.getId());
        verificationSol = taskRepository.save(verificationSol);
        
        // ... Continuer avec les autres sous-tâches et tâches principales
    }

    private void createResidentialBuildingProject(Organisation org, Member assignee, Tool tool1, Tool tool2, TaskRepository taskRepository) {
        // Tâche principale: Préparation du Site
        Task preparationSite = new Task();
        preparationSite.setDescription("Préparation du Site");
        preparationSite.setType(TaskType.SITE_PREPARATION);
        preparationSite.setCategory(TaskCategory.PROFESSIONAL);
        preparationSite.setStatus(TaskStatus.PLANNED);
        preparationSite.setAssignedMemberId(assignee.getId());
        preparationSite.setOrganisationId(org.getId());
        preparationSite.addTool(tool1);
        preparationSite = taskRepository.save(preparationSite);
        
        // ... Continuer avec les autres tâches
    }

    private void createOfficeBuildingProject(Organisation org, Member assignee, Tool tool1, Tool tool2, TaskRepository taskRepository) {
        // Tâche principale: Préparation du Site
        Task preparationSite = new Task();
        preparationSite.setDescription("Préparation du Site (Immeuble de Bureaux)");
        preparationSite.setType(TaskType.SITE_PREPARATION);
        preparationSite.setCategory(TaskCategory.PROFESSIONAL);
        preparationSite.setStatus(TaskStatus.PLANNED);
        preparationSite.setAssignedMemberId(assignee.getId());
        preparationSite.setOrganisationId(org.getId());
        preparationSite.addTool(tool1);
        preparationSite = taskRepository.save(preparationSite);
        
        // ... Continuer avec les autres tâches
    }

    private void createHospitalComplexProject(Organisation org, Member assignee, Tool tool1, Tool tool2, TaskRepository taskRepository) {
        // Tâche principale: Préparation du Site
        Task preparationSite = new Task();
        preparationSite.setDescription("Préparation du Site (Complexe Hospitalier)");
        preparationSite.setType(TaskType.SITE_PREPARATION);
        preparationSite.setCategory(TaskCategory.PROFESSIONAL);
        preparationSite.setStatus(TaskStatus.PLANNED);
        preparationSite.setAssignedMemberId(assignee.getId());
        preparationSite.setOrganisationId(org.getId());
        preparationSite.addTool(tool1);
        preparationSite = taskRepository.save(preparationSite);
        
        // ... Continuer avec les autres tâches
    }
}