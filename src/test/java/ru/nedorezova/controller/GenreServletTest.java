package ru.nedorezova.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.nedorezova.model.Author;
import ru.nedorezova.model.Book;
import ru.nedorezova.model.Genre;
import ru.nedorezova.service.BookDAOImpl;
import ru.nedorezova.service.GenreDAOImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class GenreServletTest {

    private GenreServlet genreServlet;
    private GenreDAOImpl genreDAOMock;
    private BookDAOImpl bookDAOMock;
    private HttpServletRequest requestMock;
    private HttpServletResponse responseMock;
    private RequestDispatcher dispatcherMock;

    @BeforeEach
    void setUp() {
        genreServlet = new GenreServlet(genreDAOMock);
        genreDAOMock = Mockito.mock(GenreDAOImpl.class);
        bookDAOMock = Mockito.mock(BookDAOImpl.class);
        requestMock = Mockito.mock(HttpServletRequest.class);
        responseMock = Mockito.mock(HttpServletResponse.class);
        dispatcherMock = Mockito.mock(RequestDispatcher.class);
        when(requestMock.getRequestDispatcher(anyString())).thenReturn(dispatcherMock);
    }

    @Test
    void getAllGenres_shouldForwardToJSPWithListOfGenres() throws ServletException, IOException {
        List<Genre> genres = new ArrayList<>();
        genres.add(new Genre(1, "Science Fiction"));
        genres.add(new Genre(2, "Romance"));
        when(genreDAOMock.getAllGenres()).thenReturn(genres);
        genreServlet.getAllGenres(requestMock, responseMock);
        verify(genreDAOMock).getAllGenres();
        verify(requestMock).setAttribute("genres", genres);
        verify(requestMock).getRequestDispatcher("genres.jsp");
        verify(dispatcherMock).forward(requestMock, responseMock);
    }

    @Test
    void getGenresByBook_shouldForwardToJSPWithGenresAndBook() throws ServletException, IOException {
        Book book = new Book(1, "The Hitchhiker's Guide to the Galaxy", "Science Fiction", new Author(1, "Douglas", "Adams"));
        List<Genre> genres = new ArrayList<>();
        genres.add(new Genre(1, "Science Fiction"));
        when(bookDAOMock.getBookById(1)).thenReturn(book);
        when(genreDAOMock.getGenresByBook(book)).thenReturn(genres);
        genreServlet.getGenresByBook(requestMock, responseMock, "1");
        verify(bookDAOMock).getBookById(1);
        verify(genreDAOMock).getGenresByBook(book);
        verify(requestMock).setAttribute("genres", genres);
        verify(requestMock).setAttribute("book", book);
        verify(requestMock).getRequestDispatcher("genres.jsp");
        verify(dispatcherMock).forward(requestMock, responseMock);
    }

    @Test
    void doGet_shouldCallGetAllGenresIfNoPathInfoOrPathInfoDoesNotStartWithByBook() throws ServletException, IOException {
        when(requestMock.getPathInfo()).thenReturn(null);
        genreServlet.doGet(requestMock, responseMock);
        verify(genreDAOMock).getAllGenres();
        verify(requestMock).getRequestDispatcher("genres.jsp");
        verify(dispatcherMock).forward(requestMock, responseMock);
        when(requestMock.getPathInfo()).thenReturn("/somethingelse");
        genreServlet.doGet(requestMock, responseMock);
        verify(genreDAOMock, times(2)).getAllGenres();
        verify(requestMock, times(2)).getRequestDispatcher("genres.jsp");
        verify(dispatcherMock, times(2)).forward(requestMock, responseMock);
    }

    @Test
    void doGet_shouldCallGetGenresByBookIfPathInfoStartsWithByBook() throws ServletException, IOException {
        when(requestMock.getPathInfo()).thenReturn("/byBook/1");
        genreServlet.doGet(requestMock, responseMock);
        verify(bookDAOMock).getBookById(1);
        verify(genreDAOMock).getGenresByBook(any(Book.class));
        verify(requestMock).getRequestDispatcher("genres.jsp");
        verify(dispatcherMock).forward(requestMock, responseMock);
    }
}
