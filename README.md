# Employee Management System

A full-stack web application for managing employees and departments, built with Spring Boot, React, and MySQL.

## Tech Stack

**Backend:** Java 17, Spring Boot 3.x, Spring Data JPA, Spring Security, Hibernate  
**Database:** MySQL 8+  
**Frontend:** React.js (Vite), Axios, React Router  
**API Docs:** SpringDoc OpenAPI (Swagger UI)  
**Testing:** JUnit 5, Mockito  
**CI:** GitHub Actions  

## Features

- View, add, edit, and delete employees
- Department management with Many-to-One relationship
- Filter employees by department
- Role-based access control (ADMIN and VIEWER roles)
- REST API documented with Swagger UI
- 5 unit tests with JUnit and Mockito

## Project Structure

```
├── employee-management/        # Spring Boot backend
│   └── src/main/java/com/ems/
│       ├── controller/         # REST endpoints
│       ├── service/            # Business logic
│       ├── repository/         # JPA repositories
│       ├── model/              # Entity classes
│       ├── dto/                # Data Transfer Objects
│       ├── config/             # Security, Swagger, CORS
│       └── exception/          # Custom exceptions
└── frontend/                   # React + Vite frontend
    └── src/
        ├── api/                # Axios instance
        └── components/         # React components
```

## Getting Started

### Prerequisites

- Java 17+
- Maven 3.8+
- MySQL 8+
- Node.js 18+

### 1. Set up the database

```sql
CREATE DATABASE employee_db;
```

### 2. Configure the backend

Edit `employee-management/src/main/resources/application.properties`:

```properties
spring.datasource.password=your_mysql_password
```

### 3. Run the backend

```bash
cd employee-management
./mvnw spring-boot:run
```

Backend runs at `http://localhost:8080`

### 4. Run the frontend

```bash
cd frontend
npm install
npm run dev
```

Frontend runs at `http://localhost:5173`

## API Documentation

Once the backend is running, visit:  
`http://localhost:8080/swagger-ui/index.html`

Use credentials:
- **admin / admin123** — full access (read + write)
- **viewer / viewer123** — read only

## API Endpoints

| Method | Endpoint | Description | Role |
|--------|----------|-------------|------|
| GET | `/api/employees` | Get all employees | ADMIN, VIEWER |
| GET | `/api/employees/{id}` | Get employee by ID | ADMIN, VIEWER |
| POST | `/api/employees` | Create employee | ADMIN |
| PUT | `/api/employees/{id}` | Update employee | ADMIN |
| DELETE | `/api/employees/{id}` | Delete employee | ADMIN |
| GET | `/api/departments` | Get all departments | ADMIN, VIEWER |
| POST | `/api/departments` | Create department | ADMIN |

## Running Tests

```bash
cd employee-management
./mvnw test
```
