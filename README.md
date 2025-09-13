# SUS Scheduler — Hackaton FIAP

API de agendamento de consultas do SUS, construída com **Spring Boot 3**, **Java 21**, **PostgreSQL**, **Flyway** e
documentada com **Swagger**.

## 🚀 Tecnologias

- Java 21 + Spring Boot 3
- Spring Data JPA + Hibernate
- PostgreSQL 16
- Flyway (migrations)
- MapStruct + Lombok
- Springdoc OpenAPI (Swagger UI)

## ▶️ Rodando local

1. Suba um PostgreSQL local ou use Docker.
2. Configure `src/main/resources/application.yml`:
   ```yaml
   spring:
     datasource:
       url: jdbc:postgresql://localhost:5432/sus_scheduler
       username: postgres
       password: postgres
     jpa:
       hibernate:
         ddl-auto: validate
     flyway:
       enabled: true
   ```
3. Rode a aplicação: docker compose up -d --build

## 📚 Documentação

- Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- OpenAPI JSON: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

## 🔌 Endpoints principais

- **Pacientes**: `/pacientes` → CRUD
- **Médicos**: `/medicos` → CRUD
- **Consultas**: `/consultas` → agendar, listar por paciente, cancelar