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


### Authentication and Authorization
- Created Authorization Server.
- User Auth server to get the token and refresh token.
- I have used Spring Security for authentication and authorization.
- I have used JWT for token generation and validation.
- I have used in-memory authentication for simplicity. But in real world, we can use database for authentication.
- I have used role based authorization. So, only admin can delete the user.
- I have used BCryptPasswordEncoder for password hashing.

#### User bearer token to access the products
- Get the token from the auth server.
- Use that token in the header of the request to access the products. like below
   ```bash
  curl --location 'http://localhost:8080/products/' \
  --header 'Authorization: Bearer eyJraWQiOiI4NTIzZmYyOC03ZTJhLTRmNDYtOWFhOC0yYzBiMzE3ZjRmMWUiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiYXVkIjoib2lkYy1jbGllbnQiLCJuYmYiOjE3MDgyODI2NTgsInNjb3BlIjpbInByb2ZpbGUiXSwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgxIiwiZXhwIjoxNzA4MjgyOTU4LCJpYXQiOjE3MDgyODI2NTgsImp0aSI6IjQ1NDVlYTQ5LWQxOWYtNDg1Mi05ZjRkLTgyZDQ2N2UyNzNjMiJ9.NDKn412YeoqbgwZnEXxF8ZVbT4hV4AUefKSVUM3TEQT71zir6zPhnViyzRxoeTIySt8HeaROxgKO5pz7NG3H-9aQz9WwLL9W991ki9f6kpcTOi7OJS9aLBsf7uAx7PW3Zpjtthwm7zEX9Z9MAZyEL4sV9Kp9EEyWAtmgN06zf3zBuCf1ChYAosqqRRpUMAU6t7Y0jXbfjOQ00D6Ej15xD9BhoIt51649XtoUOHeUyLOO9QXTlp-D4tKuwF9O7DoTHSJMV6gTe5A-dwhBlVcSlwfjZ-yE0VYjPuBsV_O-7aIS3qDwnE86bnzcSpWUS7UEycFknfwyV7zOgJCvH3WBRA' \
  ```
  - We can also get the new token using the refresh token. As every token has expiration time, so it will be invalid after that time.
   ```bash
  curl -X POST \
  http://localhost:8081/oauth2/token \
  -H 'Authorization: Basic b2lkYy1jbGllbnQ6c2VjcmV0' \
  -H 'Content-Type: application/x-www-form-urlencoded' \
  -H 'User-Agent: PostmanRuntime/7.36.3' \
  -H 'Accept: */*' \
  -H 'Cache-Control: no-cache' \
  -H 'Host: localhost:8081' \
  -d 'refresh_token=AbF6vsYFjdkl9FKzLgkWZpvGN8mX9540Jx8WMgk4JJhWou6ADMe2U_x8_HjxGGW_19cUoX_F-CR-5VhCxq-_AIPLtKTHZxwJTOeb8q9u5vtuz1KvILMiWRv52oykSXF4&grant_type=refresh_token'
    ```
    - Note: -H - is used to pass the header and -d is used to pass the data.
- Response: 
   ```bash
  HTTP/1.1 200 OK
  Vary: Origin
  Vary: Access-Control-Request-Method
  Vary: Access-Control-Request-Headers
  X-Content-Type-Options: nosniff
  X-XSS-Protection: 0
  Cache-Control: no-cache, no-store, max-age=0, must-revalidate
  Content-Type: application/json;charset=UTF-8
  Transfer-Encoding: chunked
  Date: Sun, 18 Feb 2024 19:00:47 GMT
  Keep-Alive: timeout=60
  Connection: keep-alive
  {
    "access_token": "eyJraWQiOiI4NTIzZmYyOC03ZTJhLTRmNDYtOWFhOC0yYzBiMzE3ZjRmMWUiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiYXVkIjoib2lkYy1jbGllbnQiLCJuYmYiOjE3MDgyODI4NDcsInNjb3BlIjpbInByb2ZpbGUiXSwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgxIiwiZXhwIjoxNzA4MjgzMTQ3LCJpYXQiOjE3MDgyODI4NDcsImp0aSI6IjEyZjdkMzc3LTc3MTctNDQ2ZC1hOGE0LTAyOGY3NTI2N2ZhMyJ9.F5z_AC4BBJyFtTlzncFOdltoCZ8UoiQQ0M_-Yr3FU_3gWK6MlTJIDb8sGxMA1fiPEvREHMLrivnOeKNu82CaIeNGN6z45Zz5-iCXnxxsNK1gSn9Om4HpWF1eLubtXUS1T_VFGKXQ-IP3r_KFTV8tLwI6tVUWEU0i2TxfqinH835M07KA2WddxovQIiZ9x92NcX2oQBaZ9TC4z-yLJbd_uqRPfN4h5V1pcPHJeuOsDbH9Un4Oz1YjLdF7mI4ae6ThVWaui_p_c4N74SS16TTpYyTZIPmDqXn1Bpri4p4Q5FpNIlKtdcVexO6DOHiWnJOD6LHwC6jUPPHL-hJ8Q0CXgA",
    "refresh_token": "AbF6vsYFjdkl9FKzLgkWZpvGN8mX9540Jx8WMgk4JJhWou6ADMe2U_x8_HjxGGW_19cUoX_F-CR-5VhCxq-_AIPLtKTHZxwJTOeb8q9u5vtuz1KvILMiWRv52oykSXF4",
    "scope": "profile",
    "token_type": "Bearer",
    "expires_in": 299
    }
  ```

## Social Profile
- [LinkedIn](https://www.linkedin.com/in/ashwanicse/)
- [Leetcode](https://leetcode.com/ashwani__kumar/)
- [Topmate](https://topmate.io/ashwanikumar)
## Linkedin Newsletter
- [Subscribe](https://www.linkedin.com/newsletters/7084124970443767808/)
