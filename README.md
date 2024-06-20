# Pet-Friendly Boat Rental Service API

Spring Boot Application with PostgreSQL Database and GraphQL

Here's the updated `README.md` to reflect the usage of Java 17:

# Project Name

Spring Boot Application with PostgreSQL Database and GraphQL

## Prerequisites

Before you begin, ensure you have met the following requirements:

- Java 17
- Maven 3.6.0 or higher
- PostgreSQL 16.3 or higher
- An IDE such as IntelliJ IDEA or Eclipse

## Installation

1. **Clone the repository:**

    ```bash
    git clone https://github.com/vpsalcedos/rental-service.git
    cd rental-service
    ```

2. **Set up PostgreSQL:**

   Ensure PostgreSQL is running. Use docker container for simplicity.

    ```bash
   docker pull postgres
   docker run --name petscreening-db -e POSTGRES_PASSWORD=p3t123456789 -d -p 5455:5432 postgres
    ```

    You can set the user, password, and database.

3. **Configure application properties:**

   Update the `application.properties` file located in `src/main/resources` with your PostgreSQL database configurations:

    ```properties
    spring.application.name=rental-service
    # PostgreSQL Database Configuration
    spring.datasource.url=jdbc:postgresql://localhost:5432/your_database_name
    spring.datasource.username=your_username
    spring.datasource.password=your_password
    # Hibernate Configuration
    spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
    spring.jpa.hibernate.ddl-auto=update
    ```

4. **Build the application:**

    ```bash
    mvn clean install
    ```

## Configuration

The application can be configured using the `application.properties` file. Key configurations include database connection details and GraphQL settings. Adjust these properties to match your environment.

## Running the Application

To run the application, use the following command:

```bash
mvn spring-boot:run
```

The application will start, and you can access it at `http://localhost:8080`.

## GraphQL Usage

Once the application is running, you can access the GraphQL playground at `http://localhost:8080/graphql`. Use this interface to run queries and mutations against your API.

### Example Queries

- **Fetch all pets:**

    ```graphql
    query { 
        pets { 
            id 
            name 
            breed 
            weight 
            vaccinated 
            level 
        } 
    }
    ```

  - **Create a new pet:**

      ```graphql
      mutation { 
          newPet(newPet: {name: "Chiqui", breed: "Doodle", weight: 12, vaccinated: true, level: 3}) { 
              id 
              name 
              breed 
              weight 
              vaccinated 
              level 
          } 
      }
      ```

      **Fetch pates by filter:**

      ```graphql
      query { 
          petsByFilter(filter: {weightUnder: 25, vaccinated: true, notBreed: "Poodle" , levelEqualsOrGreater: 3}) { 
            id 
            name 
            breed 
            weight 
            vaccinated 
            level 
          } 
      }
      ```

    **Verify pet eligibility:**

      ```graphql
      query { 
        checkPetEligibility(id: 1)
        }
      ```

## Project Structure

The project follows a standard Spring Boot structure:

```plaintext
src
├── main
│   ├── java
│   │   └── com
│   │       └── petsceening.rental_service
│   │           ├── controller
│   │           ├── exception
│   │           ├── model
│   │           ├── repository
│   │           ├── service
│   │           └── RentalServiceApplication.java
│   └── resources
│       ├── application.properties
│       └── graphql/schema.graphqls
```
