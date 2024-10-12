package ru.nedorezova.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.nedorezova.repository.AuthorDAOImpl;
import ru.nedorezova.model.Author;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.List;

public class AuthorServlet extends HttpServlet {

    private AuthorDAOImpl authorDAO;

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

        private void getAllAuthors(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            List<Author> listOfAuthors = authorDAO.getAllAuthors();
            request.setAttribute("listOfAuthors", listOfAuthors);
            RequestDispatcher dispatcher = request.getRequestDispatcher("list-of-authors.jsp");
            dispatcher.forward(request, response);
    }

    private void getAuthorById(HttpServletRequest request, HttpServletResponse response, String id) throws ServletException, IOException {
        try (Connection connection = libraryConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM authors WHERE id = ?")) {
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
        try (Connection connection = libraryConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO authors (name, surname) VALUES (?, ?)")) {
            String name = request.getParameter("name");
            String surname = request.getParameter("surname");
            statement.setString(1, "Author's name");
            statement.setString(2, "Author's surname");
            statement.executeUpdate();
            response.setStatus(HttpServletResponse.SC_CREATED);
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}