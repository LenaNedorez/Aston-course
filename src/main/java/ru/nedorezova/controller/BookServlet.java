package ru.nedorezova.controller;

import ru.nedorezova.mappers.AuthorMapper;
import ru.nedorezova.mappers.BookMapper;
import ru.nedorezova.model.Author;
import ru.nedorezova.model.Book;
import ru.nedorezova.service.AuthorDAOImpl;
import ru.nedorezova.service.BookDAOImpl;

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
 * A servlet responsible for handling requests related to books.
 * This servlet manages operations like retrieving books, displaying book details,
 * creating new books, and filtering books by author or genre.
 */
@WebServlet
public class BookServlet extends HttpServlet {

    private BookDAOImpl bookDAO;

    /**
     * Constructs an BookServlet object.
     *
     * @param bookDAO The BookDAO instance to use for data access.
     */
    public BookServlet(BookDAOImpl bookDAO) {
        this.bookDAO = bookDAO;
    }

    /**
     * Initializes the servlet by creating a new BookDAOImpl instance.
     * This method is called automatically when the servlet is loaded.
     *
     * @throws ServletException If a servlet-specific error occurs.
     */
    @Override
    public void init() throws ServletException {
        super.init();
        bookDAO = new BookDAOImpl();
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
        if (pathInfo != null && !pathInfo.isEmpty()) {
            if (pathInfo.startsWith("/byAuthor/")) {
                getBooksByAuthor(request, response, pathInfo.substring("/byAuthor/".length()));
            } else if (pathInfo.startsWith("/byGenre/")) {
                getBooksByGenre(request, response, pathInfo.substring("/byGenre/".length()));
            } else {
                getBookById(request, response, Integer.parseInt(pathInfo.substring(1)));
            }
        } else {
            getAllBooks(request, response);
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
        createBook(request, response);
    }

    /**
     * Retrieves and displays a list of all books.
     *
     * @param request  The HTTP request object.
     * @param response The HTTP response object.
     * @throws ServletException If a servlet-specific error occurs.
     * @throws IOException      If an I/O error occurs.
     */
    public void getAllBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Book> books = bookDAO.getAllBooks();
        request.setAttribute("books", books.stream()
                            .map(BookMapper.INSTANCE::toDto)
                            .collect(Collectors.toList()));
        RequestDispatcher dispatcher = request.getRequestDispatcher("books.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Retrieves and displays details of a book by its ID.
     *
     * @param request  The HTTP request object.
     * @param response The HTTP response object.
     * @param id       The ID of the book to retrieve.
     * @throws ServletException If a servlet-specific error occurs.
     * @throws IOException      If an I/O error occurs.
     */
    public void getBookById(HttpServletRequest request, HttpServletResponse response, Integer id) throws ServletException, IOException {
        Book book = bookDAO.getBookById(id);
        request.setAttribute("book", BookMapper.INSTANCE.toDto(book));
        RequestDispatcher dispatcher = request.getRequestDispatcher("book.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Creates a new book based on data from the request.
     *
     * @param request  The HTTP request object.
     * @param response The HTTP response object.
     * @throws ServletException If a servlet-specific error occurs.
     * @throws IOException      If an I/O error occurs.
     */
    public void createBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");
        String genre = request.getParameter("genre");
        Integer authorId = Integer.parseInt(request.getParameter("authorId"));
        Author author = new AuthorDAOImpl().getAuthorById(authorId);
        Book newBook = new Book();
        newBook.setTitle(title);
        newBook.setGenre(genre);
        newBook.setAuthor(author);
        bookDAO.createBook(newBook);
        response.sendRedirect("books");
    }

    /**
     * Retrieves and displays a list of books written by the specified author.
     *
     * @param request  The HTTP request object.
     * @param response The HTTP response object.
     * @param authorId The ID of the author whose books to retrieve.
     * @throws ServletException If a servlet-specific error occurs.
     * @throws IOException      If an I/O error occurs.
     */
    public void getBooksByAuthor(HttpServletRequest request, HttpServletResponse response, String authorId) throws ServletException, IOException {
        Author author = new AuthorDAOImpl().getAuthorById(Integer.parseInt(authorId));
        List<Book> books = bookDAO.getBooksByAuthor(author);
        request.setAttribute("books", books.stream()
                .map(BookMapper.INSTANCE::toDto)
                .collect(Collectors.toList()));
        request.setAttribute("author", AuthorMapper.INSTANCE.toDto(author));
        RequestDispatcher dispatcher = request.getRequestDispatcher("books.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Retrieves and displays a list of books belonging to the specified genre.
     *
     * @param request  The HTTP request object.
     * @param response The HTTP response object.
     * @param genre    The genre to filter by.
     * @throws ServletException If a servlet-specific error occurs.
     * @throws IOException      If an I/O error occurs.
     */
    public void getBooksByGenre(HttpServletRequest request, HttpServletResponse response, String genre) throws ServletException, IOException {
        List<Book> books = bookDAO.getBooksByGenre(genre);
        request.setAttribute("books", books.stream()
                .map(BookMapper.INSTANCE::toDto)
                .collect(Collectors.toList()));
        RequestDispatcher dispatcher = request.getRequestDispatcher("books.jsp");
        dispatcher.forward(request, response);
    }
}
