package ru.nedorezova.service;

import org.springframework.stereotype.Service;
import ru.nedorezova.exception.BookNotFoundException;
import ru.nedorezova.exception.GenreNotFoundException;
import ru.nedorezova.repository.GenreRepository;
import ru.nedorezova.entity.Book;
import ru.nedorezova.entity.Genre;

import java.util.List;

/**
 * Service for managing genres.
 *
 * This service provides operations for retrieving genres, including getting all
 * genres, finding genres associated with a specific book, and retrieving a
 * genre by its ID.
 */
@Service
public class GenreService {

    private final GenreRepository genreRepository;

    /**
     * Constructs a new GenreService with the given GenreRepository.
     *
     * @param genreRepository The GenreRepository to use.
     */
    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    /**
     * Retrieves a list of all genres.
     *
     * @return A list of all genres.
     */
    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    /**
     * Retrieves a list of genres associated with a specific book.
     *
     * @param book The book to retrieve genres for.
     * @return A list of genres associated with the book.
     */
    public List<Genre> getGenresByBook(Book book) {
        return book.getGenres();
    }

    /**
     * Retrieves a genre by its ID.
     *
     * @param id The ID of the genre to retrieve.
     * @return The genre with the given ID.
     * @throws GenreNotFoundException If no genre with the given ID exists.
     */
    public Genre getGenreById(Integer id) throws GenreNotFoundException { return genreRepository.findById(id)
            .orElseThrow(() -> new GenreNotFoundException("Genre not found"));
    }
}
