package ru.nedorezova.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.nedorezova.entity.Author;
import ru.nedorezova.exception.AuthorNotFoundException;
import ru.nedorezova.repository.AuthorRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class AuthorServiceTest {

    private final AuthorRepository authorRepository = Mockito.mock(AuthorRepository.class);
    private final AuthorService authorService = new AuthorService(authorRepository);

    @Test
    public void getAllAuthors_shouldReturnListOfAuthors() {
        List<Author> authors = Arrays.asList(
                new Author(1, "John", "Doe"),
                new Author(2, "Jane", "Doe")
        );

        when(authorRepository.findAll()).thenReturn(authors);
        List<Author> result = authorService.getAllAuthors();
        assertEquals(authors, result);
        verify(authorRepository, times(1)).findAll();
    }

    @Test
    public void getAuthorById_shouldReturnAuthor() throws AuthorNotFoundException {
        Author author = new Author(1, "John", "Doe");
        when(authorRepository.findById(1)).thenReturn(Optional.of(author));
        Author result = authorService.getAuthorById(1);
        assertEquals(author, result);
        verify(authorRepository, times(1)).findById(1);
    }

    @Test
    public void getAuthorById_shouldThrowAuthorNotFoundException() {
        when(authorRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(AuthorNotFoundException.class, () -> authorService.getAuthorById(1));
        verify(authorRepository, times(1)).findById(1);
    }

    @Test
    public void createAuthor_shouldSaveAuthor() {
        Author author = new Author(1, "John", "Doe");
        Author savedAuthor = new Author(1, "John", "Doe");
        when(authorRepository.save(author)).thenReturn(savedAuthor);
        Author result = authorService.createAuthor(author);
        assertEquals(savedAuthor, result);
        verify(authorRepository, times(1)).save(author);
    }
}
