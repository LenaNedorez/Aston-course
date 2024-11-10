package ru.nedorezova.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.Model;
import ru.nedorezova.dto.AuthorDto;
import ru.nedorezova.entity.Author;
import ru.nedorezova.exception.AuthorNotFoundException;
import ru.nedorezova.service.AuthorService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AuthorControllerTest {

    private final AuthorService authorService = Mockito.mock(AuthorService.class);
    private final AuthorController authorController = new AuthorController(authorService);

    @Test
    public void getAllAuthors_shouldReturnListOfAuthors() {
        List<AuthorDto> authorDtos = Arrays.asList(
                new AuthorDto(1, "John", "Doe"),
                new AuthorDto(2, "Jane", "Doe")
        );

        when(authorService.getAllAuthors()).thenReturn(Arrays.asList(
                new Author(1, "John", "Doe"),
                new Author(2, "Jane", "Doe")
        ));

        Model model = Mockito.mock(Model.class);
        String viewName = authorController.getAllAuthors(model);
        verify(authorService, times(1)).getAllAuthors();
        assertEquals("list-of-authors", viewName);
    }



    @Test
    public void getAuthorById_shouldReturnAuthor() throws AuthorNotFoundException {
        Author author = new Author(1, "John", "Doe");
        when(authorService.getAuthorById(1)).thenReturn(author);
        Model model = Mockito.mock(Model.class);
        String viewName = authorController.getAuthorById(1, model);
        verify(authorService, times(1)).getAuthorById(1);
        assertEquals("author", viewName);
    }

    @Test
    public void createAuthor_shouldCreateNewAuthor() {
        String redirectUrl = authorController.createAuthor("John", "Doe");
        verify(authorService, times(1)).createAuthor(any(Author.class));
        assertEquals("redirect:/authors", redirectUrl);
    }
}
