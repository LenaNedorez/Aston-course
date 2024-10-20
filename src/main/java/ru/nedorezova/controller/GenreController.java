package ru.nedorezova.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.nedorezova.mappers.BookMapper;
import ru.nedorezova.mappers.GenreMapper;
import ru.nedorezova.entity.Book;
import ru.nedorezova.entity.Genre;
import ru.nedorezova.service.BookService;
import ru.nedorezova.service.GenreService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class GenreController {

    private final GenreService genreService;
    private final BookService bookService;

    public GenreController(GenreService genreService, BookService bookService) {
        this.genreService = genreService;
        this.bookService = bookService;
    }

    @GetMapping("/genres")
    public String getAllGenres(Model model) {
        List<Genre> genres = genreService.getAllGenres();
        model.addAttribute("genres", genres.stream()
                .map(GenreMapper.INSTANCE::toDto)
                .collect(Collectors.toList()));
        return "genres";
    }

    @GetMapping("/genres/byBook/{bookId}")
    public String getGenresByBook(@PathVariable Integer bookId, Model model) {
        Book book = bookService.getBookById(bookId);
        List<Genre> genres = genreService.getGenresByBook(book);
        model.addAttribute("genres", genres.stream()
                .map(GenreMapper.INSTANCE::toDto)
                .collect(Collectors.toList()));
        model.addAttribute("book", BookMapper.INSTANCE.toDto(book));
        return "genres";
    }
}