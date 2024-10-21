package ru.nedorezova.service;

import org.springframework.stereotype.Service;
import ru.nedorezova.exception.AuthorNotFoundException;
import ru.nedorezova.repository.AuthorRepository;
import ru.nedorezova.entity.Author;

import java.util.List;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Author getAuthorById(Integer id) throws AuthorNotFoundException {
        return authorRepository.findById(id).orElseThrow(() -> new AuthorNotFoundException("Author not found"));
    }

    public Author createAuthor(Author author) {
        return authorRepository.save(author);
    }
}
