package ru.nedorezova.dao;

import ru.nedorezova.model.Book;
import ru.nedorezova.model.Genre;

import java.util.List;

/**
 * Data Access Object (DAO) interface for managing genre data.
 * This interface defines methods for retrieving genre information.
 */
public interface GenreDAO {

    List<Genre> getAllGenres();
    List<Genre> getGenresByBook(Book book);

}
