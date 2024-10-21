package ru.nedorezova.service;

import org.springframework.stereotype.Service;
import ru.nedorezova.exception.AuthorNotFoundException;
import ru.nedorezova.repository.AuthorRepository;
import ru.nedorezova.entity.Author;

import java.util.List;

/**
 * Service for managing authors.
 *
 * This service provides operations for retrieving, creating, and updating authors.
 */
@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    /**
     * Constructs a new AuthorService with the given AuthorRepository.
     *
     * @param authorRepository The AuthorRepository to use.
     */
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    /**
     * Retrieves a list of all authors.
     *
     * @return A list of all authors.
     */
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    /**
     * Retrieves an author by its ID.
     *
     * @param id The ID of the author to retrieve.
     * @return The author with the given ID.
     * @throws AuthorNotFoundException If no author with the given ID exists.
     */
    public Author getAuthorById(Integer id) throws AuthorNotFoundException {
        return authorRepository.findById(id).orElseThrow(() -> new AuthorNotFoundException("Author not found"));
    }

    /**
     * Creates a new author.
     *
     * @param author The author to create.
     * @return The newly created author.
     */
    public Author createAuthor(Author author) {
        return authorRepository.save(author);
    }
}
