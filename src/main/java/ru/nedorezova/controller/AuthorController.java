package ru.nedorezova.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.nedorezova.exception.AuthorNotFoundException;
import ru.nedorezova.mappers.AuthorMapper;
import ru.nedorezova.entity.Author;
import ru.nedorezova.service.AuthorService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for managing authors.
 */
@Controller
public class AuthorController {

    private final AuthorService authorService;
    private static final Logger logger = LoggerFactory.getLogger(AuthorController.class);

    /**
     * Constructs a new AuthorController with the given AuthorService.
     *
     * @param authorService The AuthorService to use.
     */
    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    /**
     * Gets a list of all authors and adds it to the model.
     *
     * @param model The model to add the authors to.
     * @return The name of the view to render.
     */
    @GetMapping("/authors")
    public String getAllAuthors(Model model) {
        List<Author> listOfAuthors = authorService.getAllAuthors();
        model.addAttribute("listOfAuthors", listOfAuthors
                .stream()
                .map(AuthorMapper.INSTANCE::toDto)
                .collect(Collectors.toList()));
        return "list-of-authors";
    }

    /**
     * Gets an author by ID and adds it to the model.
     *
     * @param id   The ID of the author to retrieve.
     * @param model The model to add the author to.
     * @return The name of the view to render.
     */
    @GetMapping("/authors/{id}")
    public String getAuthorById(@PathVariable Integer id, Model model) {
        Author author = null;
        try {
            author = authorService.getAuthorById(id);
        } catch (AuthorNotFoundException e) {
            logger.error("Error fetching author with ID: {}", id, e);
        }
        model.addAttribute("author", AuthorMapper.INSTANCE.toDto(author));
        return "author";
    }

    /**
     * Creates a new author and redirects to the list of authors.
     *
     * @param name   The name of the new author.
     * @param surname The surname of the new author.
     * @return The redirect URL.
     */
    @PostMapping("/authors")
    public String createAuthor(@RequestParam String name, @RequestParam String surname) {
        Author newAuthor = new Author();
        newAuthor.setName(name);
        newAuthor.setSurname(surname);
        authorService.createAuthor(newAuthor);
        return "redirect:/authors";
    }
}