# Bookstore REST API

A REST API for managing a bookstore, built with Spring Boot, Spring Data JPA and PostgreSQL.

## Tech stack
Java 17 · Spring Boot 3.5 · Spring Data JPA · PostgreSQL · Maven · Lombok · Bean Validation

## Features
- Full CRUD for books
- Search by author
- Request validation with clear field errors
- Global exception handling (400, 404, 409, 500)
- Layered architecture: controller, service, repository, entity, DTO

## Setup
1. Create the database and user in PostgreSQL:
```sql
   CREATE USER bookstore_user WITH PASSWORD 'your_password';
   CREATE DATABASE bookstore OWNER bookstore_user;
```
2. Set the password as an environment variable:
```powershell
   $env:DB_PASSWORD='your_password'
```
3. Run the app:
```powershell
   mvn spring-boot:run
```
   The API starts on `http://localhost:8080`.

## Endpoints
| Method | URL | Description |
|---|---|---|
| POST | `/api/books` | Create a book |
| GET | `/api/books` | List all books |
| GET | `/api/books?author=martin` | Search by author |
| GET | `/api/books/{id}` | Get one book |
| PUT | `/api/books/{id}` | Update a book |
| DELETE | `/api/books/{id}` | Delete a book |

## Example request
```json
{
  "title": "Clean Code",
  "author": "Robert C. Martin",
  "isbn": "9780132350884",
  "price": 42.50,
  "stock": 10
}
```

## Error responses
| Status | When |
|---|---|
| 400 | Validation failed or malformed JSON |
| 404 | Book not found |
| 409 | Duplicate ISBN |