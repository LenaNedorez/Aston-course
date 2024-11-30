package ru.nedorezova.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.nedorezova.dto.AuthorDto;
import ru.nedorezova.entity.Author;
import ru.nedorezova.exception.AuthorNotFoundException;
import ru.nedorezova.service.AuthorService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthorControllerTest {

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private AuthorController authorController;


    @Test
    void getAllAuthors() {
        List<Author> authors = Arrays.asList(new Author(), new Author());

        when(authorService.getAllAuthors()).thenReturn(authors);

        ResponseEntity<List<AuthorDto>> response = authorController.getAllAuthors();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertNotNull(response.getBody());

        verify(authorService, times(1)).getAllAuthors();
    }

    @Test
    void getAuthorById_found() throws AuthorNotFoundException {
        Author author = new Author();
        author.setId(1);

        when(authorService.getAuthorById(1)).thenReturn(author);

        ResponseEntity<AuthorDto> response = authorController.getAuthorById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(authorService, times(1)).getAuthorById(1);
    }

    @Test
    void getAuthorById_notFound() throws AuthorNotFoundException {
        when(authorService.getAuthorById(1)).thenThrow(new AuthorNotFoundException("Author with id 1 not found"));

        ResponseEntity<AuthorDto> response = authorController.getAuthorById(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(authorService, times(1)).getAuthorById(1);
    }

    @Test
    void createAuthor() {
        AuthorDto authorDto = new AuthorDto();
        Author createdAuthor = new Author();

        when(authorService.createAuthor(any(Author.class))).thenReturn(createdAuthor);

        ResponseEntity<AuthorDto> response = authorController.createAuthor(authorDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(authorService, times(1)).createAuthor(any(Author.class));

    }

}
