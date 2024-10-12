package ru.nedorezova.dao;

import ru.nedorezova.model.Book;
import ru.nedorezova.model.Genre;

import java.util.List;

public interface GenreDAO {

    List<Genre> getAllGenres();
    List<Genre> getGenresByBook(Book book);

}
