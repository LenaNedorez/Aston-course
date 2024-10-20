package ru.nedorezova.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.nedorezova.model.Author;
import ru.nedorezova.model.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BookDAOImplTest {

    private BookDAOImpl bookDAO;
    private Connection connectionMock;
    private PreparedStatement preparedStatementMock;
    private ResultSet resultSetMock;
    private AuthorDAOImpl authorDAOMock;

    @BeforeEach
    void setUp() {
        bookDAO = new BookDAOImpl();
        connectionMock = Mockito.mock(Connection.class);
        preparedStatementMock = Mockito.mock(PreparedStatement.class);
        resultSetMock = Mockito.mock(ResultSet.class);
        authorDAOMock = Mockito.mock(AuthorDAOImpl.class);

        when(bookDAO.getConnection()).thenReturn(connectionMock);
    }

    @Test
    void getAllBooks_shouldReturnListOfBooks() throws SQLException {
        List<Book> expectedBooks = new ArrayList<>();
        expectedBooks.add(new Book(1, "The Hitchhiker's Guide to the Galaxy", "Science Fiction", new Author(1, "Douglas", "Adams")));
        expectedBooks.add(new Book(2, "Pride and Prejudice", "Romance", new Author(2, "Jane", "Austen")));

        when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSetMock.getInt("id")).thenReturn(1).thenReturn(2);
        when(resultSetMock.getString("title")).thenReturn("The Hitchhiker's Guide to the Galaxy").thenReturn("Pride and Prejudice");
        when(resultSetMock.getString("genre")).thenReturn("Science Fiction").thenReturn("Romance");
        when(resultSetMock.getInt("author_id")).thenReturn(1).thenReturn(2);
        when(authorDAOMock.getAuthorById(1)).thenReturn(new Author(1, "Douglas", "Adams"));
        when(authorDAOMock.getAuthorById(2)).thenReturn(new Author(2, "Jane", "Austen"));

        when(connectionMock.prepareStatement("SELECT * FROM books")).thenReturn(preparedStatementMock);

        List<Book> actualBooks = bookDAO.getAllBooks();
        assertEquals(expectedBooks, actualBooks);

        verify(connectionMock, times(1)).prepareStatement("SELECT * FROM books");
        verify(preparedStatementMock, times(1)).executeQuery();
        verify(resultSetMock, times(3)).next();
        verify(resultSetMock, times(2)).getInt("id");
        verify(resultSetMock, times(2)).getString("title");
        verify(resultSetMock, times(2)).getString("genre");
        verify(resultSetMock, times(2)).getInt("author_id");
        verify(authorDAOMock, times(1)).getAuthorById(1);
        verify(authorDAOMock, times(1)).getAuthorById(2);
        verify(resultSetMock, times(1)).close();
    }

    @Test
    void getBookById_shouldReturnBookWithMatchingId() throws SQLException {
        int bookId = 1;
        Book expectedBook = new Book(bookId, "The Hitchhiker's Guide to the Galaxy", "Science Fiction", new Author(1, "Douglas", "Adams"));

        when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(true).thenReturn(false);
        when(resultSetMock.getInt("id")).thenReturn(bookId);
        when(resultSetMock.getString("title")).thenReturn("The Hitchhiker's Guide to the Galaxy");
        when(resultSetMock.getString("genre")).thenReturn("Science Fiction");
        when(resultSetMock.getInt("author_id")).thenReturn(1);
        when(authorDAOMock.getAuthorById(1)).thenReturn(new Author(1, "Douglas", "Adams"));

        when(connectionMock.prepareStatement("SELECT * FROM books WHERE id = ?")).thenReturn(preparedStatementMock);
        when(preparedStatementMock.setInt(1, bookId)).thenReturn(preparedStatementMock);

        Book actualBook = bookDAO.getBookById(bookId);
        assertEquals(expectedBook, actualBook);

        verify(connectionMock, times(1)).prepareStatement("SELECT * FROM books WHERE id = ?");
        verify(preparedStatementMock, times(1)).setInt(1, bookId);
        verify(preparedStatementMock, times(1)).executeQuery();
        verify(resultSetMock, times(2)).next();
        verify(resultSetMock, times(1)).getInt("id");
        verify(resultSetMock, times(1)).getString("title");
        verify(resultSetMock, times(1)).getString("genre");
        verify(resultSetMock, times(1)).getInt("author_id");
        verify(authorDAOMock, times(1)).getAuthorById(1);
        verify(resultSetMock, times(1)).close();
    }

    @Test
    void createBook_shouldExecuteInsertQuery() throws SQLException {
        Book book = new Book(1, "The Hitchhiker's Guide to the Galaxy", "Science Fiction", new Author(1, "Douglas", "Adams"));

        when(connectionMock.prepareStatement("INSERT INTO books (title, genre, author_id) VALUES (?, ?, ?)")).thenReturn(preparedStatementMock);
        when(preparedStatementMock.setString(1, "The Hitchhiker's Guide to the Galaxy")).thenReturn(preparedStatementMock);
        when(preparedStatementMock.setString(2, "Science Fiction")).thenReturn(preparedStatementMock);
        when(preparedStatementMock.setInt(3, 1)).thenReturn(preparedStatementMock);
        when(preparedStatementMock.executeUpdate()).thenReturn(1);
        bookDAO.createBook(book);
        verify(connectionMock, times(1)).prepareStatement("INSERT INTO books (title, genre, author_id) VALUES (?, ?, ?)");
        verify(preparedStatementMock, times(1)).setString(1, "The Hitchhiker's Guide to the Galaxy");
        verify(preparedStatementMock, times(1)).setString(2, "Science Fiction");
        verify(preparedStatementMock, times(1)).setInt(3, 1);
        verify(preparedStatementMock, times(1)).executeUpdate();
    }

    @Test
    void getBooksByAuthor_shouldReturnListOfBooksByAuthor() throws SQLException {
        Author author = new Author(1, "Douglas", "Adams");
        List<Book> expectedBooks = new ArrayList<>();
        expectedBooks.add(new Book(1, "The Hitchhiker's Guide to the Galaxy", "Science Fiction", author));
        expectedBooks.add(new Book(2, "The Restaurant at the End of the Universe", "Science Fiction", author));

        when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSetMock.getInt("id")).thenReturn(1).thenReturn(2);
        when(resultSetMock.getString("title")).thenReturn("The Hitchhiker's Guide to the Galaxy").thenReturn("The Restaurant at the End of the Universe");
        when(resultSetMock.getString("genre")).thenReturn("Science Fiction").thenReturn("Science Fiction");

        when(connectionMock.prepareStatement("SELECT * FROM books WHERE author_id = ?")).thenReturn(preparedStatementMock);
        when(preparedStatementMock.setInt(1, author.getId())).thenReturn(preparedStatementMock);

        List<Book> actualBooks = bookDAO.getBooksByAuthor(author);
        assertEquals(expectedBooks, actualBooks);

        verify(connectionMock, times(1)).prepareStatement("SELECT * FROM books WHERE author_id = ?");
        verify(preparedStatementMock, times(1)).setInt(1, author.getId());
        verify(preparedStatementMock, times(1)).executeQuery();
        verify(resultSetMock, times(3)).next();
        verify(resultSetMock, times(2)).getInt("id");
        verify(resultSetMock, times(2)).getString("title");
        verify(resultSetMock, times(2)).getString("genre");
        verify(resultSetMock, times(1)).close();
    }

    @Test
    void getBooksByGenre_shouldReturnListOfBooksByGenre() throws SQLException {
        String genre = "Science Fiction";
        List<Book> expectedBooks = new ArrayList<>();
        expectedBooks.add(new Book(1, "The Hitchhiker's Guide to the Galaxy", genre, new Author(1, "Douglas", "Adams")));
        expectedBooks.add(new Book(2, "The Restaurant at the End of the Universe", genre, new Author(1, "Douglas", "Adams")));

        when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSetMock.getInt("id")).thenReturn(1).thenReturn(2);
        when(resultSetMock.getString("title")).thenReturn("The Hitchhiker's Guide to the Galaxy").thenReturn("The Restaurant at the End of the Universe");
        when(resultSetMock.getInt("author_id")).thenReturn(1).thenReturn(1);
        when(authorDAOMock.getAuthorById(1)).thenReturn(new Author(1, "Douglas", "Adams"));

        when(connectionMock.prepareStatement("SELECT * FROM books WHERE genre = ?")).thenReturn(preparedStatementMock);
        when(preparedStatementMock.setString(1, genre)).thenReturn(preparedStatementMock);

        List<Book> actualBooks = bookDAO.getBooksByGenre(genre);
        assertEquals(expectedBooks, actualBooks);

        verify(connectionMock, times(1)).prepareStatement("SELECT * FROM books WHERE genre = ?");
        verify(preparedStatementMock, times(1)).setString(1, genre);
        verify(preparedStatementMock, times(1)).executeQuery();
        verify(resultSetMock, times(3)).next();
        verify(resultSetMock, times(2)).getInt("id");
        verify(resultSetMock, times(2)).getString("title");
        verify(resultSetMock, times(2)).getInt("author_id");
        verify(authorDAOMock, times(2)).getAuthorById(1); // Called twice for each book
        verify(resultSetMock, times(1)).close();
    }
}
