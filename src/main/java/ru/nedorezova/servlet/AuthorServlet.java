package ru.nedorezova.servlet;

import ru.nedorezova.dto.AuthorDto;
import ru.nedorezova.mappers.AuthorMapper;
import ru.nedorezova.repository.AuthorDAOImpl;
import ru.nedorezova.model.Author;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class AuthorServlet extends HttpServlet {

    private AuthorDAOImpl authorDAOIml;

    public AuthorServlet(AuthorDAOImpl authorDAOIml) {
        this.authorDAOIml = authorDAOIml;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getPathInfo();
        if (id != null && !id.isEmpty()) {
            getAuthorById(request, response, Integer.parseInt(id.substring(1)));
        } else {
            getAllAuthors(request, response);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        createAuthor(request, response);
    }

    public void getAllAuthors(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            List<Author> listOfAuthors = authorDAOIml.getAllAuthors();
            request.setAttribute("listOfAuthors", listOfAuthors);
            RequestDispatcher dispatcher = request.getRequestDispatcher("list-of-authors.jsp");
            dispatcher.forward(request, response);
    }

    public void getAuthorById(HttpServletRequest request, HttpServletResponse response, Integer id) throws ServletException, IOException {
        Author author = authorDAOIml.getAuthorById(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("author.jsp");
        request.setAttribute("author", AuthorMapper.INSTANCE.toDto(author));
        dispatcher.forward(request, response);
    }

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