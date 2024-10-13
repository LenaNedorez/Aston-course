package ru.nedorezova.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.nedorezova.dao.GenreDAO;
import ru.nedorezova.model.Book;
import ru.nedorezova.model.Genre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the GenreDAO interface using JDBC to interact with a PostgreSQL database.
 */
public class GenreDAOImpl implements GenreDAO {

    private static final Logger logger = LoggerFactory.getLogger(GenreDAOImpl.class);

    private final String jdbcUrl = "jdbc:postgresql://localhost:5432/library";
    private final String jdbcUser = "postgres";
    private final String jdbcPassword = "password";
    private final String jdbcDriver = "org.postgresql.Driver";

    public GenreDAOImpl() {}

    /**
     * Establishes a connection to the PostgreSQL database.
     *
     * @return A Connection object representing the database connection.
     */
    protected Connection getConnection() {
        Connection connection = null;

        try {
            Class.forName(jdbcDriver);
            connection = DriverManager.getConnection(jdbcUrl,jdbcUser,jdbcPassword);
        } catch (SQLException e) {
            logger.error("Error connecting to database: ", e);
        } catch (ClassNotFoundException e) {
            logger.error("JDBC driver not found: {}", jdbcDriver, e);
        }
        return connection;
    }

    /**
     * Retrieves all genres from the database.
     *
     * @return A list of Genre objects representing all genres in the database.
     */
    @Override
    public List<Genre> getAllGenres() {
        List<Genre> genres = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM genres")) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                Genre genre = new Genre(id, name);
                genres.add(genre);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return genres;
    }

    /**
     * Retrieves the genres associated with the given book.
     *
     * @param book The book to retrieve genres for.
     * @return A list of Genre objects associated with the given book.
     */
    @Override
    public List<Genre> getGenresByBook(Book book) {
        List<Genre> genres = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM genres WHERE id IN (SELECT genre_id FROM book_genres WHERE book_id = ?)")) {
            preparedStatement.setInt(1, book.getId());
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                Genre genre = new Genre(id, name);
                genres.add(genre);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return genres;
    }
}
