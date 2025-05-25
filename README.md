# Price-Comparator---Market

### Project Structure & Tech Stack Overview:

Backend Framework: Java with Spring Boot

Persistence: Spring Data JPA

Database: PostgreSQL

API Documentation: Swagger 

Architecture Pattern: Layered Architecture (Controller → Service → Repository)

Connectivity: RESTful API


### 📦 Prerequisites

- Java Development Kit (JDK) 11 or later
- PostgreSQL installed and running
- Maven or Gradle installed
- Port 8080 available (or configure an alternative)


###  Step 1: Configure the Database

Ensure PostgreSQL is installed and a database is created. The default configuration assumes a local database named `price_comparator_db`.

Update your `application.yml` with your database connection details:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/price_comparator_db
    username: postgres
    password: postgres

```

###  Step 2: Configure the Server Port 

By default, the application runs on port `8080`. If this port is already in use on your machine, you can change it by modifying the `application.yml` file. Add or update the following section:

```yaml
server:
  port: 8081  # Replace with any available port
```
### Step 3: Run the Application

Navigate to the main application class:

```yaml
price-comparator/src/main/java/com/price_comparator/PriceComparatorApplication.java
```
Application Access

Once the application is running, it will be accessible at:

```plaintext
http://localhost:8081/swagger-ui/index.html?
```

## Assumptions and Simplifications
Correctness of CSV Input
It is assumed that the CSV input adheres to the expected format and data integrity constraints. For example, date fields such as fromDate and toDate are presumed to be in chronological order.

User Authentication and Identification
User authentication and session management are handled by the frontend application. The backend API operates under the assumption that it receives a valid user identifier corresponding to the currently authenticated user.

Account Requirement for Application Usage
The application presupposes that users must have an account to utilize its features, as shopping lists and related data are associated with specific user accounts.
