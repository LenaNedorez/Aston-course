package ru.nedorezova.servlet;

import ru.nedorezova.mappers.BookMapper;
import ru.nedorezova.mappers.GenreMapper;
import ru.nedorezova.model.Book;
import ru.nedorezova.model.Genre;
import ru.nedorezova.repository.BookDAOImpl;
import ru.nedorezova.repository.GenreDAOImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class GenreController {

    private final GenreDAOImpl genreDAO;
    private final BookDAOImpl bookDAO;

    public GenreController(GenreDAOImpl genreDAO, BookDAOImpl bookDAO) {
        this.genreDAO = genreDAO;
        this.bookDAO = bookDAO;
    }

    @GetMapping("/genres")
    public String getAllGenres(Model model) {
        List<Genre> genres = genreDAO.getAllGenres();
        model.addAttribute("genres", genres.stream()
                .map(GenreMapper.INSTANCE::toDto)
                .collect(Collectors.toList()));
        return "genres";
    }

    @GetMapping("/genres/byBook/{bookId}")
    public String getGenresByBook(@PathVariable Integer bookId, Model model) {
        Book book = bookDAO.getBookById(bookId);
        List<Genre> genres = genreDAO.getGenresByBook(book);
        model.addAttribute("genres", genres.stream()
                .map(GenreMapper.INSTANCE::toDto)
                .collect(Collectors.toList()));
        model.addAttribute("book", BookMapper.INSTANCE.toDto(book));
        return "genres";
    }
}
