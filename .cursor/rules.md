# Cursor Rules for Spring Boot Project

## General Principles
- Always explain the reasoning before writing code
- Prefer clarity over cleverness
- Use simple, readable Java
- Never place business logic in controllers
- Always separate Controller, Service, Repository, Entity, DTO

## Spring Boot Specific
- Use Spring Boot 3 and Java 17
- Use constructor injection only
- Avoid field injection (@Autowired)
- Use @Service for business logic
- Use @Transactional when modifying data
- Do not use optional.get() directly

## Database & JPA
- Use Flyway for schema management
- Never use ddl-auto
- Use UUID as primary keys
- Prefer LAZY relationships
- Avoid bidirectional relationships unless necessary

## API Design
- Controllers must be thin
- Use DTOs for requests and responses
- Validate inputs using Bean Validation
- Never expose entities directly

## Security
- Use JWT for authentication
- Never store plain passwords
- Restrict access by role and ownership

## Documentation
- Add JavaDoc to services
- Keep code self-explanatory