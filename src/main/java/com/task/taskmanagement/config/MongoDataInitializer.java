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
        };
    }
}