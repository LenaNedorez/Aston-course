package ru.nedorezova.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.nedorezova.model.Author;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AuthorDAOImplTest {

    private AuthorDAOImpl authorDAO;
    private Connection connectionMock;
    private PreparedStatement preparedStatementMock;
    private ResultSet resultSetMock;

    @BeforeEach
    void setUp() {
        authorDAO = new AuthorDAOImpl();
        connectionMock = Mockito.mock(Connection.class);
        preparedStatementMock = Mockito.mock(PreparedStatement.class);
        resultSetMock = Mockito.mock(ResultSet.class);
    }

    @Test
    void getAllAuthors_shouldReturnListOfAuthors() throws SQLException {

        List<Author> expectedAuthors = new ArrayList<>();
        expectedAuthors.add(new Author(1, "John", "Doe"));
        expectedAuthors.add(new Author(2, "Jane", "Smith"));

        when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSetMock.getInt("id")).thenReturn(1).thenReturn(2);
        when(resultSetMock.getString("name")).thenReturn("John").thenReturn("Jane");
        when(resultSetMock.getString("surname")).thenReturn("Doe").thenReturn("Smith");


        when(connectionMock.prepareStatement("SELECT * FROM authors")).thenReturn(preparedStatementMock);


        List<Author> actualAuthors = authorDAO.getAllAuthors();
        assertEquals(expectedAuthors, actualAuthors);


        verify(connectionMock, times(1)).prepareStatement("SELECT * FROM authors");
        verify(preparedStatementMock, times(1)).executeQuery();
        verify(resultSetMock, times(3)).next();
        verify(resultSetMock, times(2)).getInt("id");
        verify(resultSetMock, times(2)).getString("name");
        verify(resultSetMock, times(2)).getString("surname");
        verify(resultSetMock, times(1)).close();
    }

    @Test
    void getAuthorById_shouldReturnAuthorWithMatchingId() throws SQLException {

        int authorId = 1;
        Author expectedAuthor = new Author(authorId, "John", "Doe");

        when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(true).thenReturn(false);
        when(resultSetMock.getString("name")).thenReturn("John");
        when(resultSetMock.getString("surname")).thenReturn("Doe");

        when(connectionMock.prepareStatement("SELECT * FROM authors WHERE id = ?")).thenReturn(preparedStatementMock);
        when(preparedStatementMock.setInt(1, authorId)).thenReturn(preparedStatementMock);

        Author actualAuthor = authorDAO.getAuthorById(authorId);
        assertEquals(expectedAuthor, actualAuthor);

        verify(connectionMock, times(1)).prepareStatement("SELECT * FROM authors WHERE id = ?");
        verify(preparedStatementMock, times(1)).setInt(1, authorId);
        verify(preparedStatementMock, times(1)).executeQuery();
        verify(resultSetMock, times(2)).next();
        verify(resultSetMock, times(1)).getString("name");
        verify(resultSetMock, times(1)).getString("surname");
        verify(resultSetMock, times(1)).close();
    }

    @Test
    void createAuthor_shouldExecuteInsertQuery() throws SQLException {

        Author author = new Author(1, "Jane", "Smith");

        when(connectionMock.prepareStatement("INSERT INTO authors (name, surname) VALUES (?, ?)")).thenReturn(preparedStatementMock);
        when(preparedStatementMock.setString(1, "Jane")).thenReturn(preparedStatementMock);
        when(preparedStatementMock.setString(2, "Smith")).thenReturn(preparedStatementMock);
        when(preparedStatementMock.executeUpdate()).thenReturn(1);

        authorDAO.createAuthor(author);

        verify(connectionMock, times(1)).prepareStatement("INSERT INTO authors (name, surname) VALUES (?, ?)");
        verify(preparedStatementMock, times(1)).setString(1, "Jane");
        verify(preparedStatementMock, times(1)).setString(2, "Smith");
        verify(preparedStatementMock, times(1)).executeUpdate();
    }
}