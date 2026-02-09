# Booking System API

A RESTful Booking System API built with Spring Boot 3, designed for managing services, staff availability, and customer reservations.  
It includes JWT-based authentication, PostgreSQL persistence, Flyway migrations, and OpenAPI/Swagger documentation.

---

### Table of Contents

- [Features](#features)
- [Architecture Overview](#architecture-overview)
- [Domain Model](#domain-model)
- [Tech Stack](#tech-stack)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Environment Variables](#environment-variables)
  - [Running the Application](#running-the-application)
- [API Overview](#api-overview)
  - [Authentication](#authentication)
  - [Availability](#availability)
  - [Reservations](#reservations)
  - [Services](#services)
- [Security](#security)
- [Error Handling](#error-handling)
- [Testing](#testing)
- [Possible Improvements](#possible-improvements)
- [License](#license)

---

## Features

- **JWT Authentication**
  - `/api/auth/login` endpoint that issues a JWT for authenticated users.
  - Stateless security configuration using a custom `JwtAuthenticationFilter`.

- **Reservation Management**
  - Create reservations for a given client, staff member, and service.
  - Validates that:
    - The selected service exists and has a defined duration.
    - The staff member is available at the requested time.
    - The reservation does not overlap with existing active reservations.

- **Availability Management**
  - Stores staff weekly availability (day of week, start/end time).
  - Used to validate whether a requested reservation slot is allowed.

- **Service Catalog**
  - Services with name, duration in minutes, price, and active flag.

- **Database Migrations**
  - PostgreSQL schema managed with Flyway.
  - Initial migration creates tables for users, services, availabilities, and reservations.

- **Global Error Handling**
  - Centralized exception handler for validation and authentication errors.
  - Consistent error response format.

- **OpenAPI / Swagger**
  - API documentation exposed via `springdoc-openapi` (Swagger UI).

---

## Architecture Overview

The project follows a layered architecture with clear separation of concerns:

- **Controller layer**
  - Defines HTTP endpoints for authentication and reservations.
  - Delegates business logic to service classes.

- **Service layer**
  - Contains core business rules for creating reservations.
  - Encapsulates validation logic (availability, overlapping reservations, etc.).

- **Repository layer**
  - Spring Data JPA repositories for interacting with the database.
  - Handles entities such as `ReservationEntity`, `AvailabilityEntity`, `ServiceEntity`, and `UserEntity`.

- **Security layer**
  - `JwtAuthenticationFilter` to validate JWTs on incoming requests.
  - `JwtUtil` to create and parse tokens.
  - `SecurityConfig` to configure public/protected endpoints and exception handling.

- **Common / shared**
  - `BaseEntity` with common fields (e.g. `id`, `created_at`).
  - `GlobalExceptionHandler` for consistent error responses across the API.
  - `OpenApiConfig` to configure API documentation.

---

## Domain Model

The core tables are defined via Flyway in `V1__create_initial_tables.sql`:

- **`users`**
  - `id` (UUID, PK)
  - `email`, `password`, `role`, `active`
  - `created_at`

- **`services`**
  - `id` (UUID, PK)
  - `name`, `duration_minutes`, `price`, `active`
  - `created_at`

- **`availabilities`**
  - `id` (UUID, PK)
  - `staff_id` (UUID)
  - `day_of_week`
  - `start_time`, `end_time`
  - `created_at`

- **`reservations`**
  - `id` (UUID, PK)
  - `client_id`, `staff_id`, `service_id`
  - `start_time`, `end_time`
  - `status`
  - `created_at`

These tables are mapped to the corresponding JPA entities in the `entity` packages.

---

## Tech Stack

- **Language**: Java 17
- **Framework**: Spring Boot 3 (Web, Data JPA, Security)
- **Database**: PostgreSQL
- **Migrations**: Flyway
- **Authentication**: JWT (`jjwt` library)
- **Configuration**:
  - `dotenv-java` for loading environment variables from `.env`
  - YAML-based configuration (`application.yaml`)
- **API Docs**: `springdoc-openapi-starter-webmvc-ui`
- **Testing**: JUnit 5, Mockito, Spring Security Test

---

## Getting Started

### Prerequisites

- Java **17**
- Maven **3.8+**
- PostgreSQL instance
- (Optional) `.env` file in the project root

### Environment Variables

The application expects the following environment variables (or entries in `.env`):

```env
DB_URL=jdbc:postgresql://localhost:5432/booking_db
DB_USER=your_db_user
DB_PASSWORD=your_db_password
JWT_SECRET=your_jwt_secret
```

`application.yaml` uses these variables under `spring.datasource` and `jwt.secret`.  
If you use `.env`, it is automatically loaded at startup via `Dotenv` in `BookingApplication`.

### Running the Application

1. **Clone the repository**

   ```bash
   git clone <your-repo-url>.git
   cd booking-system-api
   ```

2. **Configure the database**
   - Create a PostgreSQL database (e.g. `booking_db`).
   - Ensure `DB_URL`, `DB_USER`, `DB_PASSWORD`, and `JWT_SECRET` are set.

3. **Run database migrations**

   Flyway runs automatically on application startup and will create the necessary tables.

4. **Start the application**

   Using Maven wrapper:

   ```bash
   ./mvnw spring-boot:run
   ```

   Or with Maven installed:

   ```bash
   mvn spring-boot:run
   ```

5. **Access Swagger UI**

   Once the app is running, open:

   ```text
   http://localhost:8080/swagger-ui.html
   ```

   The OpenAPI documentation is publicly accessible.

---

## API Overview

### Authentication

Base path: `/api/auth`

- **POST `/api/auth/login`**

  **Description**: Authenticates a user with email and password and returns a JWT token.

  **Request body** (`LoginRequest`):

  ```json
  {
    "email": "user@example.com",
    "password": "your-password"
  }
  ```

  **Successful response** (`200 OK`, `LoginResponse`):

  ```json
  {
    "token": "<jwt-token>"
  }
  ```

  **Error responses**:
  - `400 Bad Request` – invalid input.
  - `401 Unauthorized` – bad credentials or disabled account (handled by global exception handler).

Use the returned JWT as a `Bearer` token in the `Authorization` header for protected endpoints:

```http
Authorization: Bearer <jwt-token>
```

### Availability

Base path: `/api/availability` (secured by JWT)

- **POST `/api/availability`**

  **Description**: Creates a new availability entry for a staff member.

  **Request body** (`CreateAvailabilityRequest`):

  ```json
  {
    "startTime": "08:00:00",
    "endTime": "17:00:00",
    "dayOfWeek": "TUESDAY"
  }
  ```

  **Successful response** (`201 Created`, `AvailabilityResponse`):

  ```json
  {
    "id": "uuid-of-availability",
    "staffId": "uuid-of-staff",
    "dayOfWeek": "TUESDAY",
    "startTime": "08:00:00",
    "endTime": "17:00:00"
  }
  ```

  **Error responses**:
  - `400 Bad Request` – invalid input.
  - `401 Unauthorized` – missing or invalid JWT.

- **GET `/api/availability`**

  **Description**: Retrieves all staff availabilities.

  **Successful response** (`200 OK`, `List<AvailabilityResponse>`):

  ```json
  [
    {
      "id": "uuid-of-availability",
      "staffId": "uuid-of-staff",
      "dayOfWeek": "MONDAY",
      "startTime": "09:00:00",
      "endTime": "18:00:00"
    }
  ]
  ```

  **Error responses**:
  - `401 Unauthorized` – missing or invalid JWT.

### Reservations

Base path: `/api/reservations` (secured by JWT)

- **POST `/api/reservations`**

  **Description**: Creates a reservation for a given client, staff member, and service, at a specified start time.

  **Request body** (simplified `CreateReservationRequest`):

  ```json
  {
    "cliendId": "uuid-of-client",
    "staffId": "uuid-of-staff",
    "serviceId": "uuid-of-service",
    "startTime": "2026-02-05T10:00:00"
  }
  ```

  **Behavior**:
  - Loads the target service to determine the duration.
  - Validates staff availability for the requested day/time.
  - Ensures there is no overlapping reservation for the staff member in an active status.
  - Creates a new `ReservationEntity` with calculated `endTime` and status.

  **Successful response** (`201 Created`, `ReservationResponse`):

  Returns the created reservation data (id, client, staff, service, start/end time, status, etc.).

  **Error responses**:
  - `400 Bad Request` – invalid request data or business rule violation (e.g. overlapping reservation, out-of-availability).
  - `401 Unauthorized` – missing or invalid JWT.

- **GET `/api/reservations`**

  **Description**: Retrieves all existing reservations.

  **Successful response** (`200 OK`, `List<ReservationResponse>`):

  ```json
  [
    {
      "id": "uuid-of-reservation",
      "clientId": "uuid-of-client",
      "staffId": "uuid-of-staff",
      "serviceId": "uuid-of-service",
      "startTime": "2026-02-05T10:00:00",
      "endTime": "2026-02-05T11:00:00",
      "status": "CONFIRMED"
    }
  ]
  ```

  **Error responses**:
  - `401 Unauthorized` – missing or invalid JWT.

### Services

Base path: `/api/service` (secured by JWT)

- **POST `/api/service`**

  **Description**: Creates a new service.

  **Request body** (`CreateServiceRequest`):

  ```json
  {
    "name": "Haircut",
    "description": "Premium haircut service",
    "durationMinutes": 30,
    "price": 25.0
  }
  ```

  **Successful response** (`201 Created`, `ServiceResponse`):

  ```json
  {
    "id": "uuid-of-service",
    "name": "Haircut",
    "description": "Premium haircut service",
    "durationMinutes": 30,
    "price": 25.0,
    "active": true
  }
  ```

  **Error responses**:
  - `400 Bad Request` – invalid input.
  - `401 Unauthorized` – missing or invalid JWT.

- **GET `/api/service`**

  **Description**: Retrieves all services.

  **Successful response** (`200 OK`, `List<ServiceResponse>`):

  ```json
  [
    {
      "id": "uuid-of-service",
      "name": "Haircut",
      "description": "Premium haircut service",
      "durationMinutes": 30,
      "price": 25.0,
      "active": true
    }
  ]
  ```

  **Error responses**:
  - `401 Unauthorized` – missing or invalid JWT.

---

## Security

Security is configured in `SecurityConfig`:

- **Public endpoints**
  - `/api/auth/**` (e.g. `/api/auth/login`)
  - `/swagger-ui/**`, `/swagger-ui.html`, `/v3/api-docs/**`
  - `/error`

- **Protected endpoints**
  - All other endpoints require a valid JWT in the `Authorization: Bearer <token>` header.

`JwtAuthenticationFilter` runs once per request and:

- Skips filtering for `POST /api/auth/login`.
- Extracts the token from the `Authorization` header.
- Validates the token with `JwtUtil` and loads the `UserDetails`.
- Sets the authenticated user in the `SecurityContext` if the token is valid.

Custom JSON responses are returned for unauthorized and forbidden requests.

---

## Error Handling

All errors are handled through a global `@RestControllerAdvice` (`GlobalExceptionHandler`):

- **Validation and business errors**
  - `IllegalArgumentException` → `400 Bad Request` with a descriptive message.

- **Authentication errors**
  - `BadCredentialsException` → `401 Unauthorized` with message `"Email o contraseña incorrectos"`.
  - `DisabledException` → `401 Unauthorized` with message `"Cuenta deshabilitada"`.
  - Other `AuthenticationException` → `401 Unauthorized` with the exception’s message.

Error responses share a common JSON structure:

```json
{
  "timestamp": "2026-02-05T12:34:56.789Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Detailed error message here"
}
```

---

## Testing

The project includes unit tests for core business logic:

- **ReservationServiceTest**
  - Verifies that creating a reservation that overlaps an existing one throws `IllegalArgumentException`.
  - Uses JUnit 5 and Mockito with mocked repositories (`ReservationRepository`, `AvailabilityRepository`, `ServiceRepository`).

Additional test classes are scaffolded for integration and controller tests and can be extended as needed.

Run tests with:

```bash
./mvnw test
# or
mvn test
```

---

## License

This project is released under the terms described in the `LICENSE` file.
