# Translation Service

This is a Spring Boot application providing a translation service with REST API endpoints.

## Design Choices

* **Microservices Architecture:** The service is designed as a standalone Spring Boot application, promoting modularity and scalability.
* **RESTful API:** The API follows REST principles, making it easy to integrate with various frontend applications.
* **JWT Authentication:** JSON Web Tokens (JWT) are used for secure authentication, ensuring that only authorized users can access the API.
* **PostgreSQL Database:** PostgreSQL is used as the database for storing translation data, offering reliability and performance.
* **Spring Data JPA:** Spring Data JPA simplifies database interactions and reduces boilerplate code.
* **Caching:** Caffeine is used for caching frequently accessed translations, improving performance.
* **CDN Support:** The service supports serving translation files from a Content Delivery Network (CDN) for better global performance.
* **Dockerization:** Docker and Docker Compose are used for containerization, making it easy to deploy and run the application in various environments.
* **Swagger/OpenAPI:** Swagger/OpenAPI documentation is provided for easy API exploration and testing.

## Setup Instructions

### Prerequisites

* Java 17 or later
* Maven or Gradle
* Docker (optional, but recommended)
* PostgreSQL (optional, if not using Docker Compose)

### Running the Application

#### Using Maven/Gradle

1.  Clone the repository.
2.  Navigate to the project directory.
3.  Build the application:
    * Maven: `mvn clean install`
    * Gradle: `gradle clean build`
4.  Run the application:
    * Maven: `mvn spring-boot:run`
    * Gradle: `gradle bootRun`
5.  Access the API: `http://localhost:8080/translations` (or `/export`, `/swagger-ui.html`)

#### Using Docker Compose (Recommended)

1.  Clone the repository.
2.  Navigate to the project directory.
3.  Build and run the application: `docker-compose up --build`
4.  Access the API: `http://localhost:8080/translations` (or `/export`, `/swagger-ui.html`)

### Configuration

* **Database:** Configure database connection details in `application.properties` or environment variables.
* **JWT:** Set the JWT secret and expiration time in `application.properties` or environment variables.
* **CDN:** Configure the CDN URL in `application.properties` or environment variables.
* **Caching:** Adjust Caffeine cache settings in `application.properties`.

### Environment Variables (Docker Compose Example)

```yaml
environment:
  - SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/translationdb
  - SPRING_DATASOURCE_USERNAME=db_user
  - SPRING_DATASOURCE_PASSWORD=db_password
  - JWT_SECRET=jwt_secret
  - CDN_URL=[https://cdn-domain.com/translations/](https://cdn-domain.com/translations/)