package ru.nedorezova.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nedorezova.dto.AuthorDto;
import ru.nedorezova.exception.AuthorNotFoundException;
import ru.nedorezova.mappers.AuthorMapper;
import ru.nedorezova.entity.Author;
import ru.nedorezova.service.AuthorService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for managing authors.
 */
@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;
    private static final Logger logger = LoggerFactory.getLogger(AuthorController.class);

    /**
     * Constructs a new AuthorController with the given AuthorService.
     *
     * @param authorService The AuthorService to use.
     */
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    /**
     * Gets a list of all authors and adds it to the model.
     *
     * @return The name of the view to render.
     */
    @GetMapping("/")
    public ResponseEntity<List<AuthorDto>> getAllAuthors() {
        List<AuthorDto> allAuthors = authorService.getAllAuthors().stream()
                .map(AuthorMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(allAuthors);
    }

    /**
     * Gets an author by ID and adds it to the model.
     *
     * @param id   The ID of the author to retrieve.
     * @return The name of the view to render.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AuthorDto> getAuthorById(@PathVariable Integer id) {
        try {
            Author author = authorService.getAuthorById(id);
            return ResponseEntity.ok(AuthorMapper.INSTANCE.toDto(author));
        } catch (AuthorNotFoundException e) {
            logger.error("Error fetching author with ID: {}", id, e);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Creates a new author and redirects to the list of authors.
     *
     * @param authorDto   The Dto of the new author.
     * @return The redirect URL.
     */
    @PostMapping("/new")
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto authorDto) {
        Author newAuthor = new Author();
        newAuthor.setName(authorDto.getName());
        newAuthor.setSurname(authorDto.getSurname());
        Author createdAuthor = authorService.createAuthor(newAuthor);
        return ResponseEntity.status(HttpStatus.CREATED).body(AuthorMapper.INSTANCE.toDto(createdAuthor));
    }

    @ExceptionHandler(AuthorNotFoundException.class)
    public ResponseEntity<String> handleAuthorNotFoundException(AuthorNotFoundException ex) {
        logger.error("Author wasn't found:", ex);
        return ResponseEntity.notFound().build();
    }
}