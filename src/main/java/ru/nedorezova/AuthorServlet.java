package ru.nedorezova;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.nedorezova.model.Author;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorServlet extends HttpServlet {

    private String jdbcUrl = System.getProperty("jdbc.url");
    private String jdbcUser = System.getProperty("jdbc.user");
    private String jdbcPassword = System.getProperty("jdbc.password");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getPathInfo();
        if (id != null && !id.isEmpty()) {
            getAuthorById(request, response, id.substring(1));
        } else {
            getAllAuthors(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        createAuthor(request, response);
    }

    protected void put(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getPathInfo();
        if (id != null && !id.isEmpty()) {
            updateAuthor(request, response, id.substring(1));
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getPathInfo();
        if (id != null && !id.isEmpty()) {
            deleteAuthor(request, response, id.substring(1));
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private void getAllAuthors(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Author> authors = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Author")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                authors.add(new Author(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("surname")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(authors);
        response.setContentType("application/json");
        response.getWriter().write(json);
    }

    private void getAuthorById(HttpServletRequest request, HttpServletResponse response, String id) throws ServletException, IOException {
        try (Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Author WHERE id = ?")) {
            statement.setInt(1, Integer.parseInt(id));
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Author author = new Author(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("surname")
                );
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(author);
            response.setContentType("application/json");
            response.getWriter().write(json);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void createAuthor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
             PreparedStatement statement = connection.prepareStatement("INSERT INTO Author (name, surname) VALUES (?, ?)")) {
            statement.setString(1, "Author's name");
            statement.setString(2, "Author's surname");
            statement.executeUpdate();
            response.setStatus(HttpServletResponse.SC_CREATED);
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void updateAuthor(HttpServletRequest request, HttpServletResponse response, String id) throws ServletException, IOException {
        try (Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
             PreparedStatement statement = connection.prepareStatement("UPDATE Author SET name = ?, surname = ? WHERE id = ?")) {
            statement.setString(1, "Author's name"); // Заменить на полученные данные
            statement.setString(2, "Author's surname"); // Заменить на полученные данные
            statement.setInt(3, Integer.parseInt(id));
            statement.executeUpdate();
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void deleteAuthor(HttpServletRequest request, HttpServletResponse response, String id) throws ServletException, IOException {
        try (Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
             PreparedStatement statement = connection.prepareStatement("DELETE FROM Author WHERE id = ?")) {
            statement.setInt(1, Integer.parseInt(id));
            statement.executeUpdate();
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}