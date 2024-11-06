package ru.nedorezova.servlet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.nedorezova.model.Author;
import ru.nedorezova.model.Book;
import ru.nedorezova.repository.AuthorDAOImpl;
import ru.nedorezova.repository.BookDAOImpl;
import ru.nedorezova.servlet.BookServlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class BookServletTest {

    private AuthorDAOImpl authorDAOMock;
    private BookServlet bookServlet;
    private BookDAOImpl bookDAOMock;
    private HttpServletRequest requestMock;
    private HttpServletResponse responseMock;
    private RequestDispatcher dispatcherMock;

    @BeforeEach
    void setUp() {
        bookServlet = new BookServlet(bookDAOMock);
        bookDAOMock = Mockito.mock(BookDAOImpl.class);
        requestMock = Mockito.mock(HttpServletRequest.class);
        responseMock = Mockito.mock(HttpServletResponse.class);
        dispatcherMock = Mockito.mock(RequestDispatcher.class);
        when(requestMock.getRequestDispatcher(anyString())).thenReturn(dispatcherMock);
    }

    @Test
    void getAllBooks_shouldForwardToJSPWithListOfBooks() throws ServletException, IOException {
        List<Book> books = new ArrayList<>();
        books.add(new Book(1, "The Hitchhiker's Guide to the Galaxy", "Science Fiction", new Author(1, "Douglas", "Adams")));
        books.add(new Book(2, "Pride and Prejudice", "Romance", new Author(2, "Jane", "Austen")));
        when(bookDAOMock.getAllBooks()).thenReturn(books);
        bookServlet.getAllBooks(requestMock, responseMock);
        verify(requestMock).setAttribute("books", books);
        verify(requestMock).getRequestDispatcher("books.jsp");
        verify(dispatcherMock).forward(requestMock, responseMock);
    }

    @Test
    void getBookById_shouldForwardToJSPWithBook() throws ServletException, IOException {
        Book book = new Book(1, "The Hitchhiker's Guide to the Galaxy", "Science Fiction", new Author(1, "Douglas", "Adams"));
        when(bookDAOMock.getBookById(1)).thenReturn(book);
        bookServlet.getBookById(requestMock, responseMock, 1);
        verify(requestMock).setAttribute("book", book);
//        verify(requestMock).getRequestDispatcher("book.jsp");
        verify(dispatcherMock).forward(requestMock, responseMock);
    }

    @Test
    void createBook_shouldCallCreateBookOnDAOAndRedirect() throws ServletException, IOException {
        when(requestMock.getParameter("title")).thenReturn("The Hitchhiker's Guide to the Galaxy");
        when(requestMock.getParameter("genre")).thenReturn("Science Fiction");
        when(requestMock.getParameter("authorId")).thenReturn("1");
        Author author = new Author(1, "Douglas", "Adams");
        when(authorDAOMock.getAuthorById(1)).thenReturn(author);
        bookServlet.createBook(requestMock, responseMock);
        verify(authorDAOMock).getAuthorById(1);
        verify(bookDAOMock).createBook(any(Book.class));
        verify(responseMock).sendRedirect("books");
    }

    @Test
    void doGet_shouldCallGetAllBooksIfNoPathInfo() throws ServletException, IOException {
        when(requestMock.getPathInfo()).thenReturn(null);
        bookServlet.doGet(requestMock, responseMock);
        verify(bookDAOMock).getAllBooks();
//        verify(requestMock).getRequestDispatcher("books.jsp");
        verify(dispatcherMock).forward(requestMock, responseMock);
    }

    @Test
    void doGet_shouldCallGetBookByIdIfIdPresent() throws ServletException, IOException {
        when(requestMock.getPathInfo()).thenReturn("/1");
        bookServlet.doGet(requestMock, responseMock);
        verify(bookDAOMock).getBookById(1);
//        verify(requestMock).getRequestDispatcher("book.jsp");
        verify(dispatcherMock).forward(requestMock, responseMock);
    }

    @Test
    void getBooksByAuthor_shouldForwardToJSPWithBooksAndAuthor() throws ServletException, IOException {
        Author author = new Author(1, "Douglas", "Adams");
        List<Book> books = new ArrayList<>();
        books.add(new Book(1, "The Hitchhiker's Guide to the Galaxy", "Science Fiction", author));
        when(authorDAOMock.getAuthorById(1)).thenReturn(author);
        when(bookDAOMock.getBooksByAuthor(author)).thenReturn(books);
        bookServlet.getBooksByAuthor(requestMock, responseMock, "1");
        verify(authorDAOMock).getAuthorById(1);
        verify(bookDAOMock).getBooksByAuthor(author);
        verify(requestMock).setAttribute("books", books);
        verify(requestMock).setAttribute("author", author);
//        verify(requestMock).getRequestDispatcher("books.jsp");
        verify(dispatcherMock).forward(requestMock, responseMock);
    }

    @Test
    void getBooksByGenre_shouldForwardToJSPWithBooksAndGenre() throws ServletException, IOException {
        List<Book> books = new ArrayList<>();
        books.add(new Book(1, "The Hitchhiker's Guide to the Galaxy", "Science Fiction", new Author(1, "Douglas", "Adams")));
        when(bookDAOMock.getBooksByGenre("Science Fiction")).thenReturn(books);
        bookServlet.getBooksByGenre(requestMock, responseMock, "Science Fiction");
        verify(bookDAOMock).getBooksByGenre("Science Fiction");
        verify(requestMock).setAttribute("books", books);
        verify(requestMock).setAttribute("genre", "Science Fiction");
//        verify(requestMock).getRequestDispatcher("books.jsp");
        verify(dispatcherMock).forward(requestMock, responseMock);
    }

    @Test
    void doGet_shouldCallGetBooksByAuthorIfPathStartsWithByAuthor() throws ServletException, IOException {
        when(requestMock.getPathInfo()).thenReturn("/byAuthor/1");
        bookServlet.doGet(requestMock, responseMock);
        verify(bookDAOMock).getBooksByAuthor(authorDAOMock.getAuthorById(1));
//        verify(requestMock).getRequestDispatcher("books.jsp");
        verify(dispatcherMock).forward(requestMock, responseMock);
    }

    @Test
    void doGet_shouldCallGetBooksByGenreIfPathStartsWithByGenre() throws ServletException, IOException {
        when(requestMock.getPathInfo()).thenReturn("/byGenre/Science Fiction");
        bookServlet.doGet(requestMock, responseMock);
        verify(bookDAOMock).getBooksByGenre("Science Fiction");
//        verify(requestMock).getRequestDispatcher("books.jsp");
        verify(dispatcherMock).forward(requestMock, responseMock);
    }
}
