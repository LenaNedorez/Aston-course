package ru.nedorezova.servlet;

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
            getAuthorById(request, response, Integer.parseInt(id.substring(1)));
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

    private void getAuthorById(HttpServletRequest request, HttpServletResponse response, Integer id) throws ServletException, IOException {
        Author author = authorDAO.getAuthorById(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("author.jsp");
        request.setAttribute("author", author);
        dispatcher.forward(request, response);
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