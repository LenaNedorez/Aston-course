package ru.nedorezova.servlet;

import ru.nedorezova.model.Author;
import ru.nedorezova.model.Book;
import ru.nedorezova.repository.AuthorDAOImpl;
import ru.nedorezova.repository.BookDAOImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class BookServlet extends HttpServlet {

    private BookDAOImpl bookDAO;

    public BookServlet(BookDAOImpl bookDAO) {
        this.bookDAO = bookDAO;
    }

    @Override
    public void init() throws ServletException {
        super.init();
        bookDAO = new BookDAOImpl();
    }

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

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        createBook(request, response);
    }

    public void getAllBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Book> books = bookDAO.getAllBooks();
        request.setAttribute("books", books);
        RequestDispatcher dispatcher = request.getRequestDispatcher("books.jsp");
        dispatcher.forward(request, response);
    }

    public void getBookById(HttpServletRequest request, HttpServletResponse response, Integer id) throws ServletException, IOException {
        Book book = bookDAO.getBookById(id);
        request.setAttribute("book", book);
        RequestDispatcher dispatcher = request.getRequestDispatcher("book.jsp");
        dispatcher.forward(request, response);
    }

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
        response.sendRedirect("books"); // Redirect to the book list
    }

    public void getBooksByAuthor(HttpServletRequest request, HttpServletResponse response, String authorId) throws ServletException, IOException {
        Author author = new AuthorDAOImpl().getAuthorById(Integer.parseInt(authorId));
        List<Book> books = bookDAO.getBooksByAuthor(author);
        request.setAttribute("books", books);
        request.setAttribute("author", author);
        RequestDispatcher dispatcher = request.getRequestDispatcher("books.jsp");
        dispatcher.forward(request, response);
    }

    public void getBooksByGenre(HttpServletRequest request, HttpServletResponse response, String genre) throws ServletException, IOException {
        List<Book> books = bookDAO.getBooksByGenre(genre);
        request.setAttribute("books", books);
        request.setAttribute("genre", genre);
        RequestDispatcher dispatcher = request.getRequestDispatcher("books.jsp");
        dispatcher.forward(request, response);
    }
}
