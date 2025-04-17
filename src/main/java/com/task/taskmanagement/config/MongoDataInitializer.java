package com.task.taskmanagement.config;

import com.task.taskmanagement.model.*;
import com.task.taskmanagement.model.enums.*;
import com.task.taskmanagement.model.tasks.*;
import com.task.taskmanagement.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

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
            organisationRepository.save(organisation1);

            Organisation organisation2 = new Organisation();
            organisation2.setName("Recyclage Montréal");
            organisationRepository.save(organisation2);

            // Rôles admin et membres
            Set<String> adminRoles = new HashSet<>();
            adminRoles.add("ROLE_ADMIN");

            Set<String> memberRoles = new HashSet<>();
            memberRoles.add("ROLE_MEMBER");

            // Création des admins
            Admin admin1 = Admin.builder()
                    .username("admin1")
                    .password(passwordEncoder.encode("Password123"))
                    .name("Abass SARR")
                    .email("abass.sarr@gmail.com")
                    .organisation(organisation1)
                    .roles(adminRoles)
                    .build();
            userRepository.save(admin1);

            Admin admin2 = Admin.builder()
                    .username("admin2")
                    .password(passwordEncoder.encode("Password123"))
                    .name("Abdou Karime Diop")
                    .email("abdou.diop@gmail.com")
                    .organisation(organisation2)
                    .roles(adminRoles)
                    .build();
            userRepository.save(admin2);

            // Création des membres
            Employee employee1 = Employee.builder()
                    .username("employee1")
                    .password(passwordEncoder.encode("Password123"))
                    .name("Mayer Paul")
                    .email("mayer.paul@gmail.com")
                    .organisation(organisation1)
                    .roles(memberRoles)
                    .score(0)
                    .build();
            userRepository.save(employee1);

            Volunteer volunteer1 = Volunteer.builder()
                    .username("volunteer1")
                    .password(passwordEncoder.encode("Password123"))
                    .name("Jane Doe")
                    .email("jane.doe@gmail.com")
                    .organisation(organisation1)
                    .roles(memberRoles)
                    .score(0)
                    .build();
            userRepository.save(volunteer1);

            // Création des outils
            Tool tool1 = ElectricTool.builder()
                    .name("Marteau électrique")
                    .available(true)
                    .organisation(organisation1)
                    .build();
            toolRepository.save(tool1);

            Tool tool2 = MechanicalTool.builder()
                    .name("Scie à chaîne")
                    .available(true)
                    .organisation(organisation1)
                    .build();
            toolRepository.save(tool2);

            // Création des tâches avec les nouveaux types
            SitePreparationTask task1 = new SitePreparationTask();
            task1.setDescription("Préparation du site");
            task1.setCategory(TaskCategory.BASIC);
            task1.setStatus(TaskStatus.PLANNED);
            task1.setComment("Arroser les plantes et les arbres du parc");
            task1.setAssignedMember(employee1);
            task1.setOrganisation(organisation1);
            task1.addTool(tool1);
            taskRepository.save(task1);

            // Ajout de sous-tâches
            Task subTask1 = new Task();
            subTask1.setDescription("Nettoyage initial");
            subTask1.setType(TaskType.SITE_PREPARATION);
            subTask1.setCategory(TaskCategory.BASIC);
            subTask1.setStatus(TaskStatus.PLANNED);
            subTask1.setParentTask(task1);
            subTask1.setAssignedMember(employee1);
            subTask1.setOrganisation(organisation1);
            taskRepository.save(subTask1);

            Task subTask2 = new Task();
            subTask2.setDescription("Marquage du terrain");
            subTask2.setType(TaskType.SITE_PREPARATION);
            subTask2.setCategory(TaskCategory.BASIC);
            subTask2.setStatus(TaskStatus.PLANNED);
            subTask2.setParentTask(task1);
            subTask2.setAssignedMember(employee1);
            subTask2.setOrganisation(organisation1);
            taskRepository.save(subTask2);

            // Mise à jour de la tâche principale avec ses sous-tâches
            task1.getSubTasks().add(subTask1);
            task1.getSubTasks().add(subTask2);
            taskRepository.save(task1);

            // Création d'autres tâches principales
            FoundationTask task2 = new FoundationTask();
            task2.setDescription("Fondations");
            task2.setCategory(TaskCategory.PROFESSIONAL);
            task2.setStatus(TaskStatus.PLANNED);
            task2.setComment("Construire les fondations");
            task2.setAssignedMember(volunteer1);
            task2.setOrganisation(organisation1);
            task2.addTool(tool2);
            taskRepository.save(task2);
        };
    }
}