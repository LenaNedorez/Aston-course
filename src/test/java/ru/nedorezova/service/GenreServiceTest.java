package ru.nedorezova.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.nedorezova.entity.Author;
import ru.nedorezova.entity.Book;
import ru.nedorezova.entity.Genre;
import ru.nedorezova.exception.GenreNotFoundException;
import ru.nedorezova.repository.GenreRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class GenreServiceTest {

    private final GenreRepository genreRepository = Mockito.mock(GenreRepository.class);
    private final GenreService genreService = new GenreService(genreRepository);

    @Test
    public void getAllGenres_shouldReturnListOfGenres() {
        List<Genre> genres = Arrays.asList(
                new Genre(1, "Genre 1"),
                new Genre(2, "Genre 2")
        );

        when(genreRepository.findAll()).thenReturn(genres);
        List<Genre> result = genreService.getAllGenres();
        assertEquals(genres, result);
        verify(genreRepository, times(1)).findAll();
    }

    @Test
    public void getGenresByBook_shouldReturnListOfGenresFromBook() {
        Book book = new Book(1, "Book Title", "Genre", new Author(1, "Author Name", "Author Surname"));
        List<Genre> genres = Arrays.asList(
                new Genre(1, "Genre 1"),
                new Genre(2, "Genre 2")
        );
        book.setGenres(genres);
        List<Genre> result = genreService.getGenresByBook(book);
        assertEquals(genres, result);
    }

    @Test
    public void getGenreById_shouldReturnGenre() throws GenreNotFoundException {
        Genre genre = new Genre(1, "Genre Name");
        when(genreRepository.findById(1)).thenReturn(Optional.of(genre));
        Genre result = genreService.getGenreById(1);
        assertEquals(genre, result);
        verify(genreRepository, times(1)).findById(1);
    }

    @Test
    public void getGenreById_shouldThrowGenreNotFoundException() {
        when(genreRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(GenreNotFoundException.class, () -> genreService.getGenreById(1));
        verify(genreRepository, times(1)).findById(1);
    }
}
