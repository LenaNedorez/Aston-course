package ru.nedorezova.repository;

import ru.nedorezova.dao.AuthorDAO;
import ru.nedorezova.model.Author;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorDAOImpl implements AuthorDAO {

    private final String jdbcUrl = "jdbc:postgresql://localhost:5432/library";
    private final String jdbcUser = "postgres";
    private final String jdbcPassword = "password";
    private final String jdbcDriver = "org.postgresql.Driver";

    public AuthorDAOImpl() {}

    protected Connection getConnection() {
        Connection connection = null;

        try {
            Class.forName(jdbcDriver);
            connection = DriverManager.getConnection(jdbcUrl,jdbcUser,jdbcPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

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
            e.printStackTrace();
        }

        return authors;
    }

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
            e.printStackTrace();
        }

        return author;
    }

    @Override
    public void createAuthor(Author author) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO authors (name, surname) VALUES (?, ?)")) {
            preparedStatement.setString(1, author.getName());
            preparedStatement.setString(2, author.getSurname());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
