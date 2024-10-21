package ru.nedorezova.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.Model;
import ru.nedorezova.dto.GenreDto;
import ru.nedorezova.entity.Author;
import ru.nedorezova.entity.Book;
import ru.nedorezova.entity.Genre;
import ru.nedorezova.exception.BookNotFoundException;
import ru.nedorezova.exception.GenreNotFoundException;
import ru.nedorezova.service.BookService;
import ru.nedorezova.service.GenreService;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class GenreControllerTest {

    private final GenreService genreService = Mockito.mock(GenreService.class);
    private final BookService bookService = Mockito.mock(BookService.class);
    private final GenreController genreController = new GenreController(genreService, bookService);

    @Test
    public void getAllGenres_shouldReturnListOfGenres() {
        when(genreService.getAllGenres()).thenReturn(Arrays.asList(
                new Genre(1, "Genre 1"),
                new Genre(2, "Genre 2")
        ));

        Model model = Mockito.mock(Model.class);
        String viewName = genreController.getAllGenres(model);
        verify(genreService, times(1)).getAllGenres();
        assertEquals("genres", viewName);
    }

    @Test
    public void getGenreById_shouldReturnGenre() throws GenreNotFoundException {
        Genre genre = new Genre(1, "Genre Name");
        GenreDto genreDto = new GenreDto(1, "Genre Name");
        when(genreService.getGenreById(1)).thenReturn(genre);

        Model model = Mockito.mock(Model.class);
        String viewName = genreController.getGenreById(1, model);
        verify(genreService, times(1)).getGenreById(1);
        assertEquals("genres", viewName);
    }

    @Test
    public void getGenresByBook_shouldReturnListOfGenresByBook() throws BookNotFoundException {
        Book book = new Book(1, "Book Title", "Genre", new Author(1, "Author Name", "Author Surname"));

        when(bookService.getBookById(1)).thenReturn(book);
        when(genreService.getGenresByBook(book)).thenReturn(Arrays.asList(
                new Genre(1, "Genre 1"),
                new Genre(2, "Genre 2")
        ));

        Model model = Mockito.mock(Model.class);
        String viewName = genreController.getGenresByBook(1, model);
        verify(bookService, times(1)).getBookById(1);
        verify(genreService, times(1)).getGenresByBook(book);
        assertEquals("genres", viewName);
    }
}
