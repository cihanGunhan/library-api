# Personal Library API

REST API for managing a personal book collection.

Built with Java 21, Spring Boot 3, PostgreSQL, and Docker.

## Requirements

- Java 21
- Docker Desktop

## Running the Application

Start the database:

```bash
docker run --name library-db \
  -e POSTGRES_PASSWORD=library123 \
  -e POSTGRES_DB=librarydb \
  -e POSTGRES_USER=libraryuser \
  -p 5432:5432 -d postgres:15
```

Run the application:

```bash
./mvnw spring-boot:run
```

## API Documentation

Swagger UI is available at `http://localhost:8080/swagger-ui/index.html` when the application is running.

## Endpoints

| Method | Path | Description |
|--------|------|-------------|
| GET | `/api/books` | List all books |
| GET | `/api/books/{id}` | Get book by ID |
| POST | `/api/books` | Create book |
| PUT | `/api/books/{id}` | Update book |
| DELETE | `/api/books/{id}` | Delete book |
| GET | `/api/books/status/{status}` | Filter by status (UNREAD, READING, COMPLETED) |
| GET | `/api/books/search?title=` | Search by title |
| GET | `/api/books/search?author=` | Search by author |

## Tech Stack

- Java 21 (Temurin LTS)
- Spring Boot 3.5
- Spring Data JPA
- PostgreSQL 15
- Docker
- Swagger / OpenAPI 3
- JUnit 5, Mockito