package ru.nedorezova.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.nedorezova.model.Author;
import ru.nedorezova.model.Book;
import ru.nedorezova.model.Genre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class GenreDAOImplTest {

    private GenreDAOImpl genreDAO;
    private Connection connectionMock;
    private PreparedStatement preparedStatementMock;
    private ResultSet resultSetMock;

    @BeforeEach
    void setUp() {
        genreDAO = new GenreDAOImpl();
        connectionMock = Mockito.mock(Connection.class);
        preparedStatementMock = Mockito.mock(PreparedStatement.class);
        resultSetMock = Mockito.mock(ResultSet.class);
        when(genreDAO.getConnection()).thenReturn(connectionMock);
    }

    @Test
    void getAllGenres_shouldReturnListOfGenres() throws SQLException {
        List<Genre> expectedGenres = new ArrayList<>();
        expectedGenres.add(new Genre(1, "Science Fiction"));
        expectedGenres.add(new Genre(2, "Romance"));
        when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSetMock.getInt("id")).thenReturn(1).thenReturn(2);
        when(resultSetMock.getString("name")).thenReturn("Science Fiction").thenReturn("Romance");
        when(connectionMock.prepareStatement("SELECT * FROM genres")).thenReturn(preparedStatementMock);
        List<Genre> actualGenres = genreDAO.getAllGenres();
        assertEquals(expectedGenres, actualGenres);
        verify(connectionMock, times(1)).prepareStatement("SELECT * FROM genres");
        verify(preparedStatementMock, times(1)).executeQuery();
        verify(resultSetMock, times(3)).next();
        verify(resultSetMock, times(2)).getInt("id");
        verify(resultSetMock, times(2)).getString("name");
        verify(resultSetMock, times(1)).close();
    }

    @Test
    void getGenresByBook_shouldReturnListOfGenresForBook() throws SQLException {
        Book book = new Book(1, "The Hitchhiker's Guide to the Galaxy", "Science Fiction", new Author(1, "Douglas", "Adams"));
        List<Genre> expectedGenres = new ArrayList<>();
        expectedGenres.add(new Genre(1, "Science Fiction"));
        when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(true).thenReturn(false);
        when(resultSetMock.getInt("id")).thenReturn(1);
        when(resultSetMock.getString("name")).thenReturn("Science Fiction");
        when(connectionMock.prepareStatement("SELECT * FROM genres WHERE id IN (SELECT genre_id FROM book_genres WHERE book_id = ?)")).thenReturn(preparedStatementMock);
        when(preparedStatementMock.setInt(1, book.getId())).thenReturn(preparedStatementMock);
        List<Genre> actualGenres = genreDAO.getGenresByBook(book);
        assertEquals(expectedGenres, actualGenres);
        verify(connectionMock, times(1)).prepareStatement("SELECT * FROM genres WHERE id IN (SELECT genre_id FROM book_genres WHERE book_id = ?)");
        verify(preparedStatementMock, times(1)).setInt(1, book.getId());
        verify(preparedStatementMock, times(1)).executeQuery();
        verify(resultSetMock, times(2)).next();
        verify(resultSetMock, times(1)).getInt("id");
        verify(resultSetMock, times(1)).getString("name");
        verify(resultSetMock, times(1)).close();
    }
}
