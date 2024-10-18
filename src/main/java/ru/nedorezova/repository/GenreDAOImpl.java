package ru.nedorezova.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.nedorezova.dao.GenreDAO;
import ru.nedorezova.model.Book;
import ru.nedorezova.model.Genre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class GenreService {

    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    public List<Genre> getGenresByBook(Book book) {
        return book.getGenres();
    }
}
