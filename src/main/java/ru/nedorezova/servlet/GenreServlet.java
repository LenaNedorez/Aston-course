package ru.nedorezova.servlet;

import ru.nedorezova.mappers.BookMapper;
import ru.nedorezova.mappers.GenreMapper;
import ru.nedorezova.model.Book;
import ru.nedorezova.model.Genre;
import ru.nedorezova.repository.BookDAOImpl;
import ru.nedorezova.repository.GenreDAOImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A servlet responsible for handling requests related to genres.
 * This servlet manages operations like retrieving all genres and displaying genres associated with a specific book.
 */
@WebServlet
public class GenreServlet extends HttpServlet {

    private GenreDAOImpl genreDAO;

    /**
     * Constructs a GenreServlet object.
     *
     * @param genreDAO The GenreDAOImpl instance to use for data access.
     */
    public GenreServlet(GenreDAOImpl genreDAO) {
        this.genreDAO = genreDAO;
    }

    /**
     * Initializes the servlet by creating a new GenreDAOImpl instance.
     * This method is called automatically when the servlet is loaded.
     *
     * @throws ServletException If a servlet-specific error occurs.
     */
    @Override
    public void init() throws ServletException {
        super.init();
        genreDAO = new GenreDAOImpl();
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
        String pathInfo = request.getPathInfo();
        if (pathInfo != null && !pathInfo.isEmpty() && pathInfo.startsWith("/byBook/")) {
            getGenresByBook(request, response, pathInfo.substring("/byBook/".length()));
        } else {
            getAllGenres(request, response);
        }
    }

    /**
     * Retrieves and displays a list of all genres.
     *
     * @param request  The HTTP request object.
     * @param response The HTTP response object.
     * @throws ServletException If a servlet-specific error occurs.
     * @throws IOException      If an I/O error occurs.
     */
    public void getAllGenres(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Genre> genres = genreDAO.getAllGenres();
        request.setAttribute("genres", genres.stream()
                .map(GenreMapper.INSTANCE::toDto)
                .collect(Collectors.toList()));
        RequestDispatcher dispatcher = request.getRequestDispatcher("genres.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Retrieves and displays a list of genres associated with a specific book.
     *
     * @param request  The HTTP request object.
     * @param response The HTTP response object.
     * @param bookId   The ID of the book to retrieve genres for.
     * @throws ServletException If a servlet-specific error occurs.
     * @throws IOException      If an I/O error occurs.
     */
    public void getGenresByBook(HttpServletRequest request, HttpServletResponse response, String bookId) throws ServletException, IOException {
        Book book = new BookDAOImpl().getBookById(Integer.parseInt(bookId));
        List<Genre> genres = genreDAO.getGenresByBook(book);
        request.setAttribute("genres", genres.stream()
                .map(GenreMapper.INSTANCE::toDto)
                .collect(Collectors.toList()));
        request.setAttribute("book", BookMapper.INSTANCE.toDto(book));
        RequestDispatcher dispatcher = request.getRequestDispatcher("genres.jsp");
        dispatcher.forward(request, response);
    }
}