package ru.nedorezova.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.nedorezova.dao.AuthorDAO;
import ru.nedorezova.model.Author;

import java.sql.*;
import java.util.ArrayList;
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

    public Author getAuthorById(Integer id) {
        return authorRepository.findById(id).orElse(null);
    }

    public Author createAuthor(Author author) {
        return authorRepository.save(author);
    }
}
