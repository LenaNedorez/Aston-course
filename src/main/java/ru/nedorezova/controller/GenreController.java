package ru.nedorezova.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nedorezova.dto.GenreDto;
import ru.nedorezova.exception.BookNotFoundException;
import ru.nedorezova.exception.GenreNotFoundException;
import ru.nedorezova.mappers.BookMapper;
import ru.nedorezova.mappers.GenreMapper;
import ru.nedorezova.entity.Book;
import ru.nedorezova.entity.Genre;
import ru.nedorezova.service.BookService;
import ru.nedorezova.service.GenreService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for managing genres.
 */
@RestController
@RequestMapping("/genres")
public class GenreController {

    private final GenreService genreService;
    private final BookService bookService;
    private static final Logger logger = LoggerFactory.getLogger(GenreController.class);

    /**
     * Constructs a new GenreController with the given GenreService and BookService.
     *
     * @param genreService The GenreService to use.
     * @param bookService  The BookService to use.
     */
    @Autowired
    public GenreController(GenreService genreService, BookService bookService) {
        this.genreService = genreService;
        this.bookService = bookService;
    }

    /**
     * Gets a list of all genres and adds it to the model.
     *
     * @return The name of the view to render.
     */
    @GetMapping
    public ResponseEntity<List<GenreDto>> getAllGenres() {
        List<GenreDto> genreDtos = genreService.getAllGenres().stream()
                .map(GenreMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(genreDtos);
    }

    /**
     * Gets a genre by ID and adds it to the model.
     *
     * @param id   The ID of the genre to retrieve.
     * @return The name of the view to render.
     */
    @GetMapping("/{id}")
    public ResponseEntity<GenreDto> getGenreById(@PathVariable Integer id) {
        try {
            Genre genre = genreService.getGenreById(id);
            return ResponseEntity.ok(GenreMapper.INSTANCE.toDto(genre));
        } catch (GenreNotFoundException e) {
            logger.error("Error fetching genre with ID: {}", id, e);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Gets a list of genres associated with a specific book and adds it to the model.
     *
     * @param bookId The ID of the book to retrieve genres for.
     * @return The name of the view to render.
     */
    @GetMapping("/byBook/{bookId}")
    public ResponseEntity<List<GenreDto>> getGenresByBook(@PathVariable Integer bookId) {
        try {
            Book book = bookService.getBookById(bookId);
            List<GenreDto> genreDtos = genreService.getGenresByBook(book).stream()
                    .map(GenreMapper.INSTANCE::toDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(genreDtos);
        } catch (BookNotFoundException e) {
            logger.error("Error fetching book with ID: {}", bookId, e);
            return ResponseEntity.notFound().build();
        }
    }
}