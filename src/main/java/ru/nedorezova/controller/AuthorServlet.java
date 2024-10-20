package ru.nedorezova.controller;

import ru.nedorezova.mappers.AuthorMapper;
import ru.nedorezova.service.AuthorDAOImpl;
import ru.nedorezova.model.Author;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


/**
 * A servlet responsible for handling requests related to authors.
 * This servlet manages operations like retrieving authors, displaying author details, and creating new authors.
 */
@WebServlet
public class AuthorServlet extends HttpServlet {

    private AuthorDAOImpl authorDAOIml;

    /**
     * Constructs an AuthorServlet object.
     *
     * @param authorDAOIml The AuthorDAOImpl instance to use for data access.
     */
    public AuthorServlet(AuthorDAOImpl authorDAOIml) {
        this.authorDAOIml = authorDAOIml;
    }

    /**
     * Handles HTTP GET requests.
     *
     * @param request  The HTTP request object.
     * @param response The HTTP response object.
     * @throws ServletException If a servlet-specific error occurs.
     * @throws IOException      If an I/O error occurs.
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getPathInfo();
        if (id != null && !id.isEmpty()) {
            getAuthorById(request, response, Integer.parseInt(id.substring(1)));
        } else {
            getAllAuthors(request, response);
        }
    }

    /**
     * Handles HTTP POST requests.
     *
     * @param request  The HTTP request object.
     * @param response The HTTP response object.
     * @throws ServletException If a servlet-specific error occurs.
     * @throws IOException      If an I/O error occurs.
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        createAuthor(request, response);
    }

    /**
     * Retrieves and displays a list of all authors.
     *
     * @param request  The HTTP request object.
     * @param response The HTTP response object.
     * @throws ServletException If a servlet-specific error occurs.
     * @throws IOException      If an I/O error occurs.
     */
    public void getAllAuthors(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            List<Author> listOfAuthors = authorDAOIml.getAllAuthors();
            request.setAttribute("listOfAuthors", listOfAuthors);
            RequestDispatcher dispatcher = request.getRequestDispatcher("list-of-authors.jsp");
            dispatcher.forward(request, response);
    }

    /**
     * Retrieves and displays details of an author by their ID.
     *
     * @param request  The HTTP request object.
     * @param response The HTTP response object.
     * @param id       The ID of the author to retrieve.
     * @throws ServletException If a servlet-specific error occurs.
     * @throws IOException      If an I/O error occurs.
     */
    public void getAuthorById(HttpServletRequest request, HttpServletResponse response, Integer id) throws ServletException, IOException {
        Author author = authorDAOIml.getAuthorById(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("author.jsp");
        request.setAttribute("author", AuthorMapper.INSTANCE.toDto(author));
        dispatcher.forward(request, response);
    }


    /**
     * Creates a new author based on data from the request.
     *
     * @param request  The HTTP request object.
     * @param response The HTTP response object.
     * @throws ServletException If a servlet-specific error occurs.
     * @throws IOException      If an I/O error occurs.
     */
    public void createAuthor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        Author newAuthor = new Author();
        newAuthor.setName(name);
        newAuthor.setSurname(surname);
        authorDAOIml.createAuthor(newAuthor);
        response.sendRedirect("list");
    }
}