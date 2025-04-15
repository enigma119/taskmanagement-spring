package com.task.taskmanagement.config;

import com.task.taskmanagement.model.*;
import com.task.taskmanagement.model.enums.Role;
import com.task.taskmanagement.model.enums.TaskStatus;
import com.task.taskmanagement.model.enums.TypeTask;
import com.task.taskmanagement.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private OrganisationRepository organisationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ToolRepository toolRepository;
    @Autowired
    private TaskRepository taskRepository;

    @Override
    public void run(String... args) throws Exception {
        // Creation des organisations
        Organisation organisation1 = new Organisation();
        organisation1.setName("Association Verte");
        organisationRepository.save(organisation1);

        Organisation organisation2 = new Organisation();
        organisation2.setName("Recyclage Montréal");
        organisationRepository.save(organisation2);

        // Creation des roles admin
        Set<String> adminRoles = new HashSet<>();
        adminRoles.add("ROLE_ADMIN");

        // Creation des roles membres
        Set<String> memberRoles = new HashSet<>();
        memberRoles.add("ROLE_MEMBER");

        // Creation des admins
        Admin admin1 = Admin.builder()
                .username("admin1")
                .password(passwordEncoder.encode("Password123"))
                .name("Abass SARR")
                .email("abass.sarr@gmail.com")
                .organisation(organisation1)
                .roles(adminRoles)
                .build();
        userRepository.save(admin1);
        organisation1.addUser(admin1);

        Admin admin2 = Admin.builder()
                .username("admin2")
                .password(passwordEncoder.encode("Password123"))
                .name("Abdou Karime Diop")
                .email("abdou.diop@gmail.com")
                .organisation(organisation2)
                .roles(adminRoles)
                .build();
        userRepository.save(admin2);
        organisation2.addUser(admin2);

        // Creation des membres
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
        organisation1.addUser(employee1);

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
        organisation1.addUser(volunteer1);

        Employee employee2 = Employee.builder()
                .username("employee2")
                .password(passwordEncoder.encode("Password123"))
                .name("Assane Kamara")
                .email("assane.kamara@gmail.com")
                .organisation(organisation2)
                .roles(memberRoles)
                .score(0)
                .build();
        userRepository.save(employee2);
        organisation2.addUser(employee2);

        Volunteer volunteer2 = Volunteer.builder()
                .username("volunteer2")
                .password(passwordEncoder.encode("Password123"))
                .name("Akram Memdou")
                .email("akram.memdou@gmail.com")
                .organisation(organisation2)
                .roles(memberRoles)
                .score(0)
                .build();
        userRepository.save(volunteer2);
        organisation2.addUser(volunteer2);

        // Creation des outils
        Tool tool1 = ElectricTool.builder()
                .name("Marteau électrique")
                .available(true)
                .organisation(organisation1)
                .build();
        toolRepository.save(tool1);
        organisation1.addTool(tool1);

        Tool tool2 = MechanicalTool.builder()
                .name("Scie à chaîne")
                .available(true)
                .organisation(organisation1)
                .build();
        toolRepository.save(tool2);
        organisation1.addTool(tool2);

        Tool tool3 = ElectricTool.builder()
                .name("Perceuse à batterie")
                .available(true)
                .organisation(organisation1)
                .build();
        toolRepository.save(tool3);
        organisation1.addTool(tool3);

        Tool tool4 = ElectricTool.builder()
                .name("Perceuse électrique")
                .available(true)
                .organisation(organisation2)
                .build();
        toolRepository.save(tool4);
        organisation2.addTool(tool4);

        Tool tool5 = MechanicalTool.builder()
                .name("Marteau")
                .available(true)
                .organisation(organisation2)
                .build();
        toolRepository.save(tool5);
        organisation2.addTool(tool5);

        Tool tool6 = ElectricTool.builder()
                .name("Scie circulaire")
                .available(true)
                .organisation(organisation2)
                .build();
        toolRepository.save(tool6);
        organisation2.addTool(tool6);

        // Creation des taches
        Task task1 = Task.builder()
                .description("Arrosage")
                .comment("Arroser les plantes et les arbres du parc")
                .type(TypeTask.BASIC)
                .status(TaskStatus.PLANNED)
                .assignedMember(employee1)
                .organisation(organisation1)
                .build();
        task1.addTool(tool1);
        taskRepository.save(task1);
        organisation1.addTask(task1);

        Task task2 = Task.builder()
                .description("Plantation")
                .comment("Planter de nouveau arbres")
                .type(TypeTask.BASIC)
                .status(TaskStatus.PLANNED)
                .assignedMember(employee1)
                .organisation(organisation1)
                .build();
        task2.addTool(tool2);
        taskRepository.save(task2);
        organisation1.addTask(task2);

        Task task3 = Task.builder()
                .description("Inspection")
                .comment("Inspecter l'etat des panneaux solaires")
                .type(TypeTask.BASIC)
                .status(TaskStatus.PLANNED)
                .assignedMember(volunteer1)
                .organisation(organisation1)
                .build();
        task3.addTool(tool3);
        taskRepository.save(task3);
        organisation1.addTask(task3);

        Task task4 = Task.builder()
                .description("Nettoyage")
                .comment("Nettoyer le parc et ramasser les déchets")
                .type(TypeTask.BASIC)
                .status(TaskStatus.PLANNED)
                .assignedMember(volunteer2)
                .organisation(organisation2)
                .build();
        task4.addTool(tool4);
        taskRepository.save(task4);
        organisation2.addTask(task4);

        Task task5 = Task.builder()
                .description("Installation")
                .comment("Installer des équipements de recyclage")
                .type(TypeTask.BASIC)
                .status(TaskStatus.PLANNED)
                .assignedMember(employee2)
                .organisation(organisation2)
                .build();
        task5.addTool(tool5);
        taskRepository.save(task5);
        organisation2.addTask(task5);

        Task task6 = Task.builder()
                .description("Audit")
                .comment("Effectuer un audit de recyclage")
                .type(TypeTask.BASIC)
                .status(TaskStatus.PLANNED)
                .assignedMember(volunteer2)
                .organisation(organisation2)
                .build();
        task6.addTool(tool6);
        taskRepository.save(task6);
        organisation2.addTask(task6);

        // Sauvegarde des organisations avec toutes les relations
        organisationRepository.save(organisation1);
        organisationRepository.save(organisation2);
    }
}