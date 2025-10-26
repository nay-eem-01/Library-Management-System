# Library Management System

A simple library management system which includes full-text search capabilities using Hibernate Search.


## Table of Contents
- [About](#about)  
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Architecture & Project Structure](#architecture-project-structure)
- [Getting Started](#getting-started)  
   - [Prerequisites](#prerequisites)  
   - [Installation](#installation)  
   - [Configuration](#configuration)  
   - [Running the Application](#running-the-application)
-  [Usage](#usage)  
   - [CRUD Operations](#crud-operations)  
   - [Full-Text Search](#full-text-search)  
7. [Database Schema](#database-schema)  
---

## About
This Library Management System is a Java-based backend application built with Spring Boot and Hibernate (including Hibernate Search) to manage typical library operations: tracking books, members, loans, and enabling full-text search over the library’s catalogue. It is intended as a demonstration project and can be extended for production use.

## Features
- Create, Read, Update, Delete (CRUD) operations for key entity Book.
- Full-text search over book titles/authors/contents via Hibernate Search.  
- Spring Boot application with RESTful API endpoints.  
- Database persistence using Oracle (or any relational database supported by JPA/Hibernate).  
- Clean, modular project structure ready for further extension (e.g., roles, permissions, UI front-end).  
---

## Technologies Used
- Java  
- Spring Boot  
- Spring Data JPA  
- Hibernate ORM  
- Hibernate Search (for full-text indexing/search) & Lucene backend 
- Oracle Database
- Docker
- Maven (build & dependency management)
---
## Architecture & Project Structure

```bash
Library-Management-System/
├── src/
│ ├── main/
│ │ ├── java/com.disl.librarymanagementsystem/
│ │ │ ├── controller/
│ │ │ │ ├── BookController.java
│ │ │ ├── service/
│ │ │ │ ├── BookService.java
│ │ │ │ ├── IndexService.java
│ │ │ ├── serviceImpl/
│ │ │ │ ├── BookServiceImpl.java
│ │ │ ├── entity/
│ │ │ │ ├── Book.java
│ │ │ ├── repository/
│ │ │ │ ├── BookRepository.java
│ │ │ ├── model/
│ │ │ │ ├── request/
│ │ │ │ └── response/
│ │ │ │   ├── BookResponse.java
│ │ │ │   ├── HttpResponse.java
│ │ │ ├── config/
│ │ │ │ ├── AppConfig.java
│ │ │ │ ├── CustomLuceneAnalysisConfigurer.java
│ │ │ └── LibraryManagementSystemApplication.java
│ │ └── resources/
│ │ └── application.properties
│ └── test/
├── pom.xml
└── README.md
```
---
### Key Concepts
- **Entities** – represent core domain objects (e.g., `Book`, `Member`, `Loan`).  
- **Repositories** – extend `JpaRepository` for standard persistence.  
- **Services** – encapsulate business logic and interact with repositories.  
- **Controllers** – expose REST endpoints (e.g., `/api/books`, `/api/search`).  
- **Search** – Hibernate Search indexes selected entity fields enabling full-text queries.
---
## Getting Started

## Prerequisites

Before running this application, ensure you have the following installed:

- Java 17 (or compatible version)  
- Maven (or your build tool of choice)  
- Oracle DB (Use Docker container)
- Elasticsearch or embedded Lucene backend  
- Git (for cloning repository)
---
### Verify Installation

```bash
# Clone the repository
git clone https://github.com/nay-eem-01/Library-Management-System.git
cd Library-Management-System
```
---
# Build the project

```bash
mvn clean install
```
---
# Configuration

1. Open src/main/resources/application.properties

2. Configure the database connection:

```bash
spring.application.name=LibraryManagementSystem

server.port=8081

# Oracle DB connection
spring.datasource.url=jdbc:oracle:thin:@//localhost:1521/xe
spring.datasource.username=system
spring.datasource.password=oracle
spring.datasource.driver-class-name=oracle.jdbc.OracleDriver

# JPA / Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.OracleDialect

spring.jpa.properties.hibernate.search.backend.type=lucene
spring.jpa.properties.hibernate.search.backend.directory.root=./indexes
spring.jpa.properties.hibernate.search.backend.directory.filesystem_access.strategy=auto
spring.jpa.properties.hibernate.search.backend.analysis.configurer=class:com.disl.librarymanagementsystem.config.CustomLuceneAnalysisConfigurer

# Logging Configuration
logging.level.root=INFO
logging.level.org.example.bankingManagementApplication=DEBUG
```
---
# Running the Application
```bash
mvn spring-boot:run
```
---
### API Documentation

Base URL
http://localhost:8081/api

## API Endpoints

### 1. Add new book

**Endpoint:** `POST /api/book/add`

**Request Body:**

```json
{
  "title": "book-title",
  "author": "book-author-name",
  "isbn": "book-isbn-number",
  "iaAvailable": "true/false"
}
```

**Response Body:**

```json
{
    "status": "CREATED",
    "message": "Book created successfully",
    "payload": null,
    "success": true
}
```
---

### 2. Get All Books

**Endpoint:** `GET /api/book/all`

**Response Body:**

```json
{
   "status": "OK",
    "message": "Data loaded successfully",
    "payload": [
        {
            "id": 2,
            "title": "Java tutorial",
            "author": "Alex mark",
            "available": true,
            "isbn": "1234"
        },
        {
            "id": 45,
            "title": "The Great Gatsby",
            "author": "F. Scott Fitzgerald",
            "available": false,
            "isbn": "9780743273565"
        }
    ],
  "success": true
}

```
---

### 3. Search book 

**Endpoint:** `GET /api/book/search-book`

**Request parameter:**
```txt
keyword : value
```
**Response Body:**

```json
{
    "status": "OK",
    "message": "Data loaded successfully",
    "payload": [
        {
            "id": 52,
            "title": "Moby Dick",
            "author": "Herman Melville",
            "available": false,
            "isbn": "9781503280786"
        }
    ],
    "success": true
}
```
---
# Database Schema

| **Entity** | **Fields** |
|-------------|------------------------|
| **Book**    | `id`, `title`, `author`, `isbn`, `isAvailable`, `indexedFields` |

