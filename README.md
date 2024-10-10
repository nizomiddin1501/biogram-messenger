This project is an Biogram messenger developed using Spring Boot and PostgreSQL. The system provides APIs for managing creating posts and comments, create account, and report different posts. It also includes Swagger for API documentation.

Table of Contents
Features
Technologies
Setup Instructions
Running the Application
API Documentation
Report Types
Contact
Features
CRUD Operations: Create, read, update, and delete users' data.
Posts different types researches: Generate reports based on category, posts, comment, and blog. 
Swagger Integration: Automatically generated API documentation.
Database Support: PostgreSQL for persistent data storage.
Technologies
Java 17
Spring Boot (Backend framework)
PostgreSQL (Database)
Swagger (API documentation)
Maven (Build tool)
RESTful API (For communication between client and server)
Setup Instructions
Prerequisites
Ensure you have the following installed:

Java 17
PostgreSQL
Maven
Database Setup
Create a PostgreSQL database:
CREATE DATABASE biogram-messenger;
Update your application.properties file with the correct database credentials:
spring.application.name=biogram-messenger
spring.datasource.url=jdbc:postgresql://localhost:5432/biogram-messenger
spring.datasource.username=postgres
spring.datasource.password=1234
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
springdoc.swagger-ui.enabled=true 
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

Running the Application
Clone the repository:
git clone https://github.com/nizomiddin1501/biogram-messenger cd biogram-messenger

Install the dependencies and build the project:
mvn clean install

Run the Spring Boot application:
mvn spring-boot:run

The application will be available at http://localhost:8080.
API Documentation
The API documentation is available via Swagger. Once the application is running, navigate to the following URL to view the Swagger UI:

http://localhost:8080/swagger-ui/index.html

Swagger UI provides an interactive interface for testing the APIs. You can view all available endpoints, their descriptions, and test them directly from the browser.

Report Types
The system supports the following reposts types for user to search different posts:

CATEGORY: Users' different posts category. 
POST: Posts posted by users 
COMMENT: Comments written by users on posts. 
BLOG: Blogs created by users to discuss various topics.

Contact
For any inquiries or issues, feel free to contact:

Name: Nizomiddin Mirzanazarov Email: nizomiddinmirzanazarov@gmail.com 
My portfolio: https://nizomiddin-portfolio.netlify.app/
