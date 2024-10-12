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

    protected Connection getConnection() throws {
        Connection connection = null;

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(jdbcUrl,jdbcUser,jdbcPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }


    public List<Author> getAllAuthors() {

        List <Author> authors = new ArrayList<Author>();
        try(Connection connection = getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM authors");){
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
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
}
