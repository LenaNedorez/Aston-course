package ru.nedorezova.servlet;

import ru.nedorezova.model.Book;
import ru.nedorezova.model.Genre;
import ru.nedorezova.repository.BookDAOImpl;
import ru.nedorezova.repository.GenreDAOImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GenreServlet extends HttpServlet {

    private GenreDAOImpl genreDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        genreDAO = new GenreDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo != null && !pathInfo.isEmpty() && pathInfo.startsWith("/byBook/")) {
            getGenresByBook(request, response, pathInfo.substring("/byBook/".length()));
        } else {
            getAllGenres(request, response);
        }
    }

    private void getAllGenres(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Genre> genres = genreDAO.getAllGenres();
        request.setAttribute("genres", genres);
        RequestDispatcher dispatcher = request.getRequestDispatcher("genres.jsp");
        dispatcher.forward(request, response);
    }

    private void getGenresByBook(HttpServletRequest request, HttpServletResponse response, String bookId) throws ServletException, IOException {
        Book book = new BookDAOImpl().getBookById(Integer.parseInt(bookId));
        List<Genre> genres = genreDAO.getGenresByBook(book);
        request.setAttribute("genres", genres);
        request.setAttribute("book", book);
        RequestDispatcher dispatcher = request.getRequestDispatcher("genres.jsp");
        dispatcher.forward(request, response);
    }
}