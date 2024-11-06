package ru.nedorezova.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.nedorezova.dao.AuthorDAO;
import ru.nedorezova.model.Author;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the AuthorDAO interface using JDBC to interact with a PostgreSQL database.
 */
public class AuthorDAOImpl implements AuthorDAO {

    private static final Logger logger = LoggerFactory.getLogger(AuthorDAOImpl.class);
    private final String jdbcUrl = "jdbc:postgresql://localhost:5432/library";
    private final String jdbcUser = "postgres";
    private final String jdbcPassword = "password";
    private final String jdbcDriver = "org.postgresql.Driver";

    public AuthorDAOImpl() {}

    /**
     * Establishes a connection to the PostgreSQL database.
     *
     * @return A Connection object representing the database connection.
     */
    protected Connection getConnection() {
        Connection connection = null;

        try {
            Class.forName(jdbcDriver);
            connection = DriverManager.getConnection(jdbcUrl,jdbcUser,jdbcPassword);
        } catch (SQLException e) {
            logger.error("Error connecting to database: ", e);
        } catch (ClassNotFoundException e) {
            logger.error("JDBC driver not found: {}", jdbcDriver, e);
        }
        return connection;
    }

    /**
     * Retrieves all authors from the database.
     *
     * @return A list of Author objects representing all authors in the database.
     */
    @Override
    public List<Author> getAllAuthors() {
        List <Author> authors = new ArrayList<>();
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM authors");){
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt("id");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                authors.add(new Author(id, name, surname));
            }
            rs.close();
        } catch (SQLException e) {
            logger.error("Error getting all authors: ", e);
        }

        return authors;
    }

    /**
     * Retrieves an author from the database by their ID.
     *
     * @param id The ID of the author to retrieve.
     * @return An Author object representing the author with the specified ID.
     */
    @Override
    public Author getAuthorById(Integer id) {
        Author author = new Author();
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM authors WHERE id = ?");){
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                author = new Author(id, name, surname);
            }
            rs.close();
        } catch (SQLException e) {
            logger.error("Error getting author by id: {}", id, e);
        }

        return author;
    }

    /**
     * Creates a new author in the database.
     *
     * @param author The Author object to create.
     */
    @Override
    public void createAuthor(Author author) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO authors (name, surname) VALUES (?, ?)")) {
            preparedStatement.setString(1, author.getName());
            preparedStatement.setString(2, author.getSurname());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error creating author: {}", author, e);
        }
    }
}
