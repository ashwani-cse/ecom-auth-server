# user-management-service

This repository contains a Spring Boot project named **user-management-service**. The project is built using Maven and includes controllers, a repository, a service, and exception handling.

## Table of Contents
- [Overview](#overview)
- [Project Structure](#project-structure)
- [Usage](#usage)
- [Build](#build)

## Overview

The project consists of a Spring Boot application with the following components:

1. **Main Application Class:** `UserManagementServiceApplication`
    - Entry point for the application.
    - Annotated with `@SpringBootApplication`.

2. **Controller Class:** `UserManagementController`
    - RESTful controller handling user-related requests.

## Project Structure


## Usage

1. Clone the repository:

    ```bash
    git clone https://github.com/ashwani-cse/user-management-service.git
    ```

2. Build the project:

    ```bash
    mvn clean install -U
    ```
-U: It is a optional to update snapshots and releases.
3. Run the application:

    ```bash
    mvn spring-boot:run
    ```

4. Access the API:

   Open a web browser or a tool like [Postman](https://www.postman.com/) and make a GET request to `http://localhost:8080/users/`.

## SQL Queries executed
```mysql
CREATE database IF NOT EXISTS user_management_service;
CREATE USER IF NOT EXISTS  user_management_service_admin IDENTIFIED BY 'user_management_service_admin';
GRANT ALL PRIVILEGES ON user_management_service.* TO user_management_service_admin;
```
#### Make some security changes before exposing application to user. Because you may be exposed to SQL injection attacks.
- Command to revoke all the privileges from the user associated with the application:
   ```mysql
    revoke all on user_management_service.* from 'user_management_service_admin';
    ```
- And give some necessary privileges to application to make changes to only data of the database.
   ```mysql
    grant select, insert, delete, update on user_management_service.* to 'user_management_service_admin';
    ```
#### When you want to make changes to the database:
-  Re-grant permissions.
- Change the spring.jpa.hibernate.ddl-auto =  update
- Re-run your applications.

Then repeat the two commands shown here to make your application safe for production use again. Better still, use a dedicated migration tool, such as Flyway or Liquibase.


#### OWASP Dependency-Check tool :
This is a popular open-source tool that helps identify project dependencies and check if they have known, publicly disclosed, vulnerabilities.
To identify and report known vulnerabilities in the dependencies of a Maven-based project execute below command -
   ```bash
    mvn dependency-check:check 
   ```
Note: Use the NVD API key for fast scanning.

If you see any vulnerability, try to update with latest version of that dependency.
In this project i found vulnerability with jackson-databind-core in 2.15.x versions.
So i updated pom with dependency management with latest jackson-databind core version.

Note: for github use to commit/push and pull
create a token from developer settings then in-place of password enter that token. Because from 2021, password has disabled for security reason.

## Social Profile
- [LinkedIn](https://www.linkedin.com/in/ashwanicse/)
- [Leetcode](https://leetcode.com/ashwani__kumar/)
- [Topmate](https://topmate.io/ashwanikumar)
## Linkedin Newsletter
- [Subscribe](https://www.linkedin.com/newsletters/7084124970443767808/)
