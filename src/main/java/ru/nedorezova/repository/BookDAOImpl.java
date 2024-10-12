package ru.nedorezova.repository;

import ru.nedorezova.dao.BookDAO;
import ru.nedorezova.model.Author;
import ru.nedorezova.model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAOImpl implements BookDAO {

    private final String jdbcUrl = "jdbc:postgresql://localhost:5432/library";
    private final String jdbcUser = "postgres";
    private final String jdbcPassword = "password";
    private final String jdbcDriver = "org.postgresql.Driver";

    public BookDAOImpl() {}

    protected Connection getConnection() {
        Connection connection = null;

        try {
            Class.forName(jdbcDriver);
            connection = DriverManager.getConnection(jdbcUrl,jdbcUser,jdbcPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    @Override
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM books")) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt("id");
                String title = rs.getString("title");
                String genre = rs.getString("genre");
                Integer authorId = rs.getInt("author_id");
                Author author = new AuthorDAOImpl().getAuthorById(authorId);
                Book book = new Book(id, title, genre, author);
                books.add(book);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public Book getBookById(Integer id) {
        Book book = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM books WHERE id = ?")) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                String title = rs.getString("title");
                String genre = rs.getString("genre");
                Integer authorId = rs.getInt("author_id");
                Author author = new AuthorDAOImpl().getAuthorById(authorId);
                book = new Book(id, title, genre, author);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return book;
    }

    @Override
    public void createBook(Book book) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO books (title, genre, author_id) VALUES (?, ?, ?)")) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getGenre());
            preparedStatement.setInt(3, book.getAuthor().getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Book> getBooksByAuthor(Author author) {
        List<Book> books = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM books WHERE author_id = ?")) {
            preparedStatement.setInt(1, author.getId());
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt("id");
                String title = rs.getString("title");
                String genre = rs.getString("genre");
                Book book = new Book(id, title, genre, author);
                books.add(book);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public List<Book> getBooksByGenre(String genre) {
        List<Book> books = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM books WHERE genre = ?")) {
            preparedStatement.setString(1, genre);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt("id");
                String title = rs.getString("title");
                Integer authorId = rs.getInt("author_id");
                Author author = new AuthorDAOImpl().getAuthorById(authorId);
                Book book = new Book(id, title, genre, author);
                books.add(book);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }
}
