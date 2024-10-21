## Task 3 of Aston intensive course

### Task description

The task is to upgrade the project which was made before (task 2). 
In this project there can be used Spring, Hibernate etc technologies except fot Spring Boot.

### Project description

This project implements a small Library System using Spring MVC. It allows to manage books, 
authors, and genres with basic CRUD operations.

### Features

    Authors:
        Create new authors
        Retrieve all authors
        Retrieve an author by ID
    Books:
        Create new books
        Retrieve all books
        Retrieve a book by ID
        Retrieve books by author
        Retrieve books by genre
    Genres:
        Retrieve all genres
        Retrieve a genre by ID
        Retrieve genres associated with a book

### Technologies

- Spring MVC
- Spring Data JPA
- Hibernate
- Postgres
- Lombok
- MapStruct
- SLF4J
- Junit 5
- Mockito

### Getting Started


- Clone the repository: ```git clone https://github.com/your-username/book-management-system.git```
- Navigate to the project directory: ```cd book-management-system```
- Build the project: ```mvn clean install```
- Run the application: ```mvn spring-boot:run```
- Access the application: ```Open your browser and go to http://localhost:8080/```

### Running Tests
You can run the unit tests using the following command: ```mvn test```

### Contributing and thanks

Contributions are welcome! Feel free to submit pull requests or open issues.
Thanks to Aston for providing an opportunity to write this project 
and to all the developers of the technologies used. 
And special thanks to the people who will read this and point out errors in the project.