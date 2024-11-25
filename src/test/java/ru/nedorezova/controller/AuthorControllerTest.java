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
import ru.nedorezova.mappers.AuthorMapper;
import ru.nedorezova.service.AuthorService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthorControllerTest {

    @Mock
    private AuthorService authorService;

    @Mock
    private AuthorMapper authorMapper;

    @InjectMocks
    private AuthorController authorController;


    @Test
    void getAllAuthors() {
        List<Author> authors = Arrays.asList(new Author(), new Author());
        List<AuthorDto> authorDtos = Arrays.asList(new AuthorDto(), new AuthorDto());

        when(authorService.getAllAuthors()).thenReturn(authors);
        when(authorMapper.toDto(any(Author.class))).thenReturn(new AuthorDto());


        List<AuthorDto> result = authorController.getAllAuthors();

        assertEquals(authorDtos.size(), result.size());
        verify(authorService, times(1)).getAllAuthors();
        verify(authorMapper, times(authors.size())).toDto(any(Author.class));
    }

    @Test
    void getAuthorById_found() throws AuthorNotFoundException {
        Author author = new Author();
        author.setId(1);
        AuthorDto authorDto = new AuthorDto();

        when(authorService.getAuthorById(1)).thenReturn(author);
        when(authorMapper.toDto(author)).thenReturn(authorDto);

        ResponseEntity<AuthorDto> response = authorController.getAuthorById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(authorDto, response.getBody());
        verify(authorService, times(1)).getAuthorById(1);
        verify(authorMapper, times(1)).toDto(author);
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
        authorDto.setName("Test");
        authorDto.setSurname("Author");
        Author author = new Author();
        author.setName("Test");
        author.setSurname("Author");
        Author createdAuthor = new Author();
        createdAuthor.setId(1);


        when(authorService.createAuthor(any(Author.class))).thenReturn(createdAuthor);
        when(authorMapper.toDto(createdAuthor)).thenReturn(authorDto);

        ResponseEntity<AuthorDto> response = authorController.createAuthor(authorDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(authorDto, response.getBody());
        verify(authorService, times(1)).createAuthor(any(Author.class));
        verify(authorMapper, times(1)).toDto(createdAuthor);

    }

}
