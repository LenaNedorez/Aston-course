package ru.nedorezova.repository;

import ru.nedorezova.dao.GenreDAO;
import ru.nedorezova.model.Book;
import ru.nedorezova.model.Genre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GenreDAOImpl implements GenreDAO {

    private final String jdbcUrl = "jdbc:postgresql://localhost:5432/library";
    private final String jdbcUser = "postgres";
    private final String jdbcPassword = "password";
    private final String jdbcDriver = "org.postgresql.Driver";

    public GenreDAOImpl() {}

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
