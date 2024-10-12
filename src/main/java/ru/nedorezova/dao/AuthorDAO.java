package ru.nedorezova.dao;

import ru.nedorezova.model.Author;

import java.util.List;

public interface AuthorDAO {

    List<Author> getAllAuthors();
    public Author getAuthorById();
}
