# Application de Gestion des Tâches

## Auteurs

* **Étudiant 1** : Abdoul Karime DIOP / **Courriel:** diop.abdoul_karime@courrier.uqam.ca/ ****Code Permenant**:**DIOA14279808
* **Étudiant 2** : Abass SARR / **Courriel:** sarr.abass@courrier.uqam.ca/ ****Code Permenant**:**SARA07349709


## Structure du Projet

Le projet est divisé en deux parties distinctes :

### Partie 1 : Application Console

- Située dans le package `com.task.taskmanagement.partie1`
- Exécutable via la commande Java directe :

```bash
java -cp target/classes com.task.taskmanagement.partie1.Main
```

- Interface utilisateur en mode console
- Gestion locale des données

### Partie 2 : Application Spring Boot

- Point d'entrée : `com.task.taskmanagement.TaskmanagementApplication`
- Nous vous avons mis à disposition une collection PostMan  pour faciliter les test : **Task Management System.postman_collection.json**
  Il suffit de l'importer sur postman pour tester les endpoints
- Démarrage avec Maven :

```bash
mvn spring-boot:run
```

- Interface web moderne avec API REST
- Base de données H2 pour la persistance
- Sécurité intégrée avec Spring Security
- Architecture MVC complète

## Configuration

L'application Spring Boot utilise :

- Une base de données H2 (en mémoire)
- Spring Security pour la sécurité
- Spring Data JPA pour la persistance des données

## Base de données H2

La console H2 est accessible à l'adresse : `http://localhost:8080/h2-console`

Paramètres de connexion :

- JDBC URL : `jdbc:h2:mem:taskdb`
- Utilisateur : `sa`
- Mot de passe : `password`

## Choix de l'Exécution

- Pour une utilisation rapide et locale : utilisez la Partie 1 (console)
- Pour une application complète avec interface web : utilisez la Partie 2 (Spring Boot)

## Note Importante

Les deux parties sont indépendantes et ne partagent pas les mêmes données.

- La partie console gère ses données en mémoire pendant l'exécution
- La partie Spring Boot utilise une base de données H2 et offre une persistance des données pendant la session
