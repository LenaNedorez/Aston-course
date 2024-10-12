# Task 2 of Aston intensive course

## Task description

The task is to make a REST service. Use servlets and JDBC technologies, also there should be many to many, one to many connections.

## Project description

This project implements a REST API for a library, managing book, author and genre data using servlets and JDBC.

## Description of the data model

#### Author (Author): 
- id (int, primary key) 
- first name (String) 
- last name (String) 
#### Book (Book): 
- id (int, primary key) 
- title (String) 
- genre (String) 
- author (Author, one-to-many) 
#### Genre (Genre): 
- id (int, primary key) 
- title (String) 
- book (Book, many-to-many)

## REST API
1. Author:

- GET /authors: Returns a list of all authors.
- GET /authors/{id}: Returns information about the author with the specified id.
- POST /authors: Creates a new author.
- PUT /authors/{id}: Updates the author with the specified id.

2. Book:

- GET /books: Returns a list of all books.
- GET /books/{id}: Returns information about the book with the specified id.
- POST /books: Creates a new book.
- PUT /books/{id}: Updates the book with the specified id.
- DELETE /books/{id}: Deletes the book with the specified id.

3. Genre:

- GET /genres: Returns a list of all genres.
- GET /genres/{id}: Returns information about the genre with the specified id.
- GET /genres/{id}/books: Returns a list of books in the genre with the specified id.

## Technologies

- Java Servlet API: Servlets for handling HTTP requests.
- JDBC: Access to MySQL database.
- JSON: Data format for data exchange.
- Maven: Managing dependencies and building a project.
