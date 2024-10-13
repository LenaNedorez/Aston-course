## Task 2 of Aston intensive course

### Task description

The task is to make a REST service. Use servlets and JDBC technologies, also there should be many to many, one to many connections.

### Project description

This is a web application that allows you to manage your library: add authors, books, genres and search for information on them.

### Technologies

- Java: Programming language.
- Servlet: Servlet API for handling HTTP requests.
- JDBC: Interface for interacting with databases.
- PostgreSQL: Relational database management system.
- MapStruct: Library for automatically creating mappers.
- SLF4J: Library for working with logging.
- (Potentially) JSP - JavaServer Pages: Technology for creating dynamic web pages.

### Project structure

- Model: Classes representing domain objects (Author, Book, Genre, AuthorDto, BookDto, GenreDto).
- DAO: DAO interfaces (AuthorDAO, BookDAO, GenreDAO) and their implementations (AuthorDAOImpl, BookDAOImpl, GenreDAOImpl).
- Servlet: Servlets (AuthorServlet, BookServlet, GenreServlet) for handling requests.
- Mapper: Mapper interfaces (AuthorMapper, BookMapper, GenreMapper).

### Functionality

- Add Authors: Add new authors to the database.
- Add Books: Add new books, including author and genre.
- View All Books: Display a list of all books.
- Search Books: Search books by author, genre, or title.
- View Book Details: Display information about a book, including author, genre, and description.
- View All Authors: Display a list of all authors.
- View Author Details: Display information about an author, including a list of their books.
- View All Genres: Display a list of all genres.
- View Books by Genre: Display a list of all books of a specific genre.

### Starting the project

- Setup: Make sure you have PostgreSQL and Java installed.
- Starting the server: Start the application server (e.g. Tomcat), configuring it to work with the web application.

### Small summary
During the project, a REST service for library management was successfully developed. 
The application is implemented on the basis of servlets and uses JDBC to interact with the PostgreSQL database. 
The service provides functionality for working with authors, books and genres, 
demonstrating the implementation of many-to-many (book - genre) and one-to-many (book - author) relationships.

### Thanks
Thanks to Aston for providing an opportunity to write this project and to all the developers of the technologies used.
And special thanks to the people who will read this and point out errors in the project.

