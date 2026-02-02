# Booking System API

A professional REST API for managing reservations, built with Spring Boot 3 and PostgreSQL.

## 🚀 Features
- User authentication with JWT
- Role-based access control
- Service and availability management
- Reservation system with collision validation
- PostgreSQL with Flyway migrations
- OpenAPI / Swagger documentation

## 🛠️ Tech Stack
- Java 17
- Spring Boot 3
- Spring Security 6
- PostgreSQL (Neon)
- Flyway
- Maven

## 📦 Project Structure
The project follows a layered architecture:
- Controller: HTTP layer
- Service: Business logic
- Repository: Database access
- DTO: Data transfer objects

## 🔐 Roles
- ADMIN: Full access
- STAFF: Manage reservations
- CLIENT: Create and manage own reservations

## ⚙️ Configuration

Environment variables required:
```env
DB_URL=
DB_USER=
DB_PASSWORD=
JWT_SECRET=