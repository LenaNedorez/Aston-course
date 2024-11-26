package ru.nedorezova.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.nedorezova.dto.GenreDto;
import ru.nedorezova.entity.Book;
import ru.nedorezova.entity.Genre;
import ru.nedorezova.exception.BookNotFoundException;
import ru.nedorezova.exception.GenreNotFoundException;
import ru.nedorezova.service.BookService;
import ru.nedorezova.service.GenreService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GenreControllerTest {

    @Mock
    private GenreService genreService;

    @Mock
    private BookService bookService;

    @InjectMocks
    private GenreController genreController;


    @Test
    void getAllGenres() {
        List<Genre> genres = Arrays.asList(new Genre(), new Genre());
        List<GenreDto> genreDtos = Arrays.asList(new GenreDto(), new GenreDto());

        when(genreService.getAllGenres()).thenReturn(genres);

        ResponseEntity<List<GenreDto>> response = genreController.getAllGenres();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(genreDtos.size(), response.getBody().size());
        verify(genreService, times(1)).getAllGenres();
    }

    @Test
    void getGenreById_found() throws GenreNotFoundException {
        Genre genre = new Genre();
        genre.setId(1);
        GenreDto genreDto = new GenreDto();

        when(genreService.getGenreById(1)).thenReturn(genre);

        ResponseEntity<GenreDto> response = genreController.getGenreById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(genreDto.getId(), response.getBody().getId());
        verify(genreService, times(1)).getGenreById(1);
    }

    @Test
    void getGenreById_notFound() throws GenreNotFoundException {
        when(genreService.getGenreById(1)).thenThrow(new GenreNotFoundException("Genre with id 1 not found"));

        ResponseEntity<GenreDto> response = genreController.getGenreById(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(genreService, times(1)).getGenreById(1);
    }

    @Test
    void getGenresByBook_found() throws BookNotFoundException {
        Book book = new Book();
        book.setId(1);
        List<Genre> genres = Arrays.asList(new Genre(), new Genre());
        List<GenreDto> genreDtos = Arrays.asList(new GenreDto(), new GenreDto());

        when(bookService.getBookById(1)).thenReturn(book);
        when(genreService.getGenresByBook(book)).thenReturn(genres);

        ResponseEntity<List<GenreDto>> response = genreController.getGenresByBook(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(genreDtos.size(), response.getBody().size());
        verify(bookService, times(1)).getBookById(1);
        verify(genreService, times(1)).getGenresByBook(book);
    }

    @Test
    void getGenresByBook_bookNotFound() throws BookNotFoundException {
        when(bookService.getBookById(1)).thenThrow(new BookNotFoundException("Book with id 1 not found"));

        ResponseEntity<List<GenreDto>> response = genreController.getGenresByBook(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(bookService, times(1)).getBookById(1);
        verify(genreService, never()).getGenresByBook(any(Book.class));
    }

}
