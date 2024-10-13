import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.nedorezova.model.Author;
import ru.nedorezova.repository.AuthorDAOImpl;
import ru.nedorezova.servlet.AuthorServlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AuthorServletTest {

    private AuthorServlet authorServlet;
    private AuthorDAOImpl authorDAOMock;
    private HttpServletRequest requestMock;
    private HttpServletResponse responseMock;
    private RequestDispatcher dispatcherMock;

    @BeforeEach
    void setUp() {
        authorServlet = new AuthorServlet(authorDAOMock);
        authorDAOMock = Mockito.mock(AuthorDAOImpl.class);
        requestMock = Mockito.mock(HttpServletRequest.class);
        responseMock = Mockito.mock(HttpServletResponse.class);
        dispatcherMock = Mockito.mock(RequestDispatcher.class);
        when(requestMock.getRequestDispatcher(anyString())).thenReturn(dispatcherMock);
    }

    @Test
    void getAllAuthors_shouldForwardToJSPWithListOfAuthors() throws ServletException, IOException {
        List<Author> authors = new ArrayList<>();
        authors.add(new Author(1, "John", "Doe"));
        authors.add(new Author(2, "Jane", "Smith"));
        when(authorDAOMock.getAllAuthors()).thenReturn(authors);

        authorServlet.getAllAuthors(requestMock, responseMock);

        verify(requestMock).setAttribute("listOfAuthors", authors);
//        verify(requestMock).getRequestDispatcher("list-of-authors.jsp");
        verify(dispatcherMock).forward(requestMock, responseMock);
    }

    @Test
    void getAuthorById_shouldForwardToJSPWithAuthor() throws ServletException, IOException {
        Author author = new Author(1, "John", "Doe");
        when(authorDAOMock.getAuthorById(1)).thenReturn(author);

        authorServlet.getAuthorById(requestMock, responseMock, 1);

        verify(requestMock).setAttribute("author", author);
//        verify(requestMock).getRequestDispatcher("author.jsp");
        verify(dispatcherMock).forward(requestMock, responseMock);
    }

    @Test
    void createAuthor_shouldCallCreateAuthorOnDAOAndRedirect() throws ServletException, IOException {
        when(requestMock.getParameter("name")).thenReturn("John");
        when(requestMock.getParameter("surname")).thenReturn("Doe");

        authorServlet.createAuthor(requestMock, responseMock);

        verify(authorDAOMock).createAuthor(any(Author.class));
        verify(responseMock).sendRedirect("list");
    }

    @Test
    void doGet_shouldCallGetAllAuthorsIfNoId() throws ServletException, IOException {
        when(requestMock.getPathInfo()).thenReturn(null);

        authorServlet.doGet(requestMock, responseMock);

        verify(authorDAOMock).getAllAuthors();
//        verify(requestMock).getRequestDispatcher("list-of-authors.jsp");
        verify(dispatcherMock).forward(requestMock, responseMock);
    }

    @Test
    void doGet_shouldCallGetAuthorByIdIfIdPresent() throws ServletException, IOException {
        when(requestMock.getPathInfo()).thenReturn("/1");

        authorServlet.doGet(requestMock, responseMock);

        verify(authorDAOMock).getAuthorById(1);
//        verify(requestMock).getRequestDispatcher("author.jsp");
        verify(dispatcherMock).forward(requestMock, responseMock);
    }
}