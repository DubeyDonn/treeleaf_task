# Treeleaf Project

## Overview

This project is a Spring Boot application that provides a blogging platform with user authentication and authorization.

## Features

- User registration and login
- Role-based access control (Admin and User roles)
- CRUD operations for blogs
- File upload for blog thumbnails
- JWT-based authentication

## Technologies Used

- Java 17
- Spring Boot 3.4.0
- Spring Security
- JJWT 0.11.5
- MySQL

## Getting Started

### Prerequisites

- Java 17
- Maven
- MySQL

### Installation

1. Clone the repository:

   ```sh
   git clone https://github.com/yourusername/treeleaf.git
   cd treeleaf
   ```

2. Set up the database:

   ```sql
   CREATE DATABASE treeleaf_test;
   ```

3. Update the database configuration in `src/main/resources/application.properties`:

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3307/treeleaf_test
   spring.datasource.username=root
   spring.datasource.password=root
   ```

4. Build the project:

   ```sh
   ./mvnw clean install
   ```

5. Run the application:
   ```sh
   ./mvnw spring-boot:run
   ```

## API Endpoints

### Authentication (No Authentication Required)

- `POST /user/register` - Register a new user
- `POST /login` - Authenticate a user and get a JWT

### Authentication (Admin Authentication Required)

- `POST /admin/register` - Register a new admin

### Blogs (Authentication Required)

- `GET /blogs` - Get all blogs
- `GET /blog/{id}` - Get a blog by ID

### Blogs (Admin Authentication Required)

- `POST /admin/blog` - Create a new blog
- `PUT /admin/blog/{id}` - Update a blog
- `DELETE /admin/blog/{id}` - Delete a blog

### Comments (Authentication Required)

- `POST /comment/{blogId}` - Create a new comment for a blog

## Postman Collection

You can find the Postman collection for testing the API endpoints [here](TreeLeaf.postman_collection.json).
You can import this collection into Postman and start testing the API endpoints. REMEMBER to update the JWT token in the collection with the token you get after logging in. Also remember to update urls in the collection to match your local environment.

## Acknowledgements

- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Security](https://spring.io/projects/spring-security)
- [JJWT](https://github.com/jwtk/jjwt)
- [MySQL](https://www.mysql.com/)
