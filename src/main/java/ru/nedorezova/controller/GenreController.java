package ru.nedorezova.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
@Controller
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
     * @param model The model to add the genres to.
     * @return The name of the view to render.
     */
    @GetMapping("/genres")
    public String getAllGenres(Model model) {
        List<Genre> genres = genreService.getAllGenres();
        model.addAttribute("genres", genres.stream()
                .map(GenreMapper.INSTANCE::toDto)
                .collect(Collectors.toList()));
        return "genres";
    }

    /**
     * Gets a genre by ID and adds it to the model.
     *
     * @param id   The ID of the genre to retrieve.
     * @param model The model to add the genre to.
     * @return The name of the view to render.
     */
    @GetMapping("/genres/{id}")
    public String getGenreById(@PathVariable Integer id, Model model) {
        Genre genre = null;
        try {
            genre = genreService.getGenreById(id);
        } catch (GenreNotFoundException e) {
            logger.error("Error fetching genre with ID: {}", id, e);
        }
        model.addAttribute("genre", GenreMapper.INSTANCE.toDto(genre));
        return "genres";
    }

    /**
     * Gets a list of genres associated with a specific book and adds it to the model.
     *
     * @param bookId The ID of the book to retrieve genres for.
     * @param model  The model to add the genres to.
     * @return The name of the view to render.
     */
    @GetMapping("/genres/byBook/{bookId}")
    public String getGenresByBook(@PathVariable Integer bookId, Model model) {
        Book book = null;
        try {
            book = bookService.getBookById(bookId);
        } catch (BookNotFoundException e) {
            logger.error("Error fetching book with ID: {}", bookId, e);
        }
        List<Genre> genres = genreService.getGenresByBook(book);
        model.addAttribute("genres", genres.stream()
                .map(GenreMapper.INSTANCE::toDto)
                .collect(Collectors.toList()));
        model.addAttribute("book", BookMapper.INSTANCE.toDto(book));
        return "genres";
    }
}