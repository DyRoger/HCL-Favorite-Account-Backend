# HCL Favorite Account Backend

This repository contains the backend service for the "Favorite Accounts" application, built with Spring Boot. It provides a RESTful API to manage favorite accounts, handle user authentication via JWT, and integrate with banking services.

## Technologies Used

*   **Java 21**
*   **Spring Boot:** Powering the core application structure (Web, Data JPA, Security, Validation).
*   **PostgreSQL:** Relational database for storing user, favorite accounts, bank mappings, and audit logs.
*   **JWT (JSON Web Tokens):** For secure authentication and authorization.
*   **Lombok:** To reduce boilerplate code.
*   **Maven:** For dependency management and project build.

## Prerequisites

Before running the application, ensure you have the following installed:

*   [Java Development Kit (JDK) 21](https://jdk.java.net/21/)
*   [Apache Maven](https://maven.apache.org/)
*   [PostgreSQL](https://www.postgresql.org/)

## Database Configuration

The application requires a PostgreSQL database to be running locally on the default port (`5432`). 

You need to create a database, user, and password that matches the configuration in `src/main/resources/application.yaml`.

```yaml
# Expected default configuration in application.yaml:
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/hclBanking
    username: postgres
    password: abcd1234
```

To set this up, run the following SQL commands in your PostgreSQL environment:

```sql
CREATE DATABASE "hclBanking";
-- Make sure the user 'postgres' has the password 'abcd1234'.
```

## Running the Application

1.  **Clone the repository** (if you haven't already):
    ```bash
    git clone <repository-url>
    cd HCL-Favorite-Account-Backend
    ```

2.  **Build the project** using Maven:
    ```bash
    mvn clean install
    ```

3.  **Run the application**:
    ```bash
    mvn spring-boot:run
    ```
    
    The application will start on `http://localhost:8080`.

## Project Structure

*   `src/main/java/org/bank/hcl`: The root package for the source code.
    *   `controller`: REST controllers managing API endpoints (e.g., `AuthController`, `FavoriteAccountController`, `BankingController`).
    *   `service`: Business logic for authentication, accounts, banking, and audit logging.
    *   `repository`: Spring Data JPA interfaces for database interaction.
    *   `model`: JPA entities mapping to database tables (`User`, `FavoriteAccount`, `BankMapping`, `AuditLog`).
    *   `dto`: Data Transfer Objects used to receive requests and send responses.
    *   `config`: Security configuration and JWT filters.
    *   `exception`: Global exception handling and custom exception classes.

## Features

*   **Authentication:** Secure endpoints using JWT. Includes user login processing.
*   **Favorite Account Management:** CRUD operations to add, view, update, and delete favorite bank accounts.
*   **Banking Service:** Retrieve and map bank codes to display human-readable bank names.
*   **Audit Logging:** Tracking internal requests or errors for accountability and monitoring.

## Security Configurations

The application relies on JWT for securing the endpoints. The default `application.yaml` provides basic properties for JWT expiration and a secret key. In production, these should be securely injected via environment variables:
*   `AUTH_LOGIN_HASH`
*   `AUTH_JWT_SECRET`
*   `AUTH_JWT_EXPIRATION_MS`
