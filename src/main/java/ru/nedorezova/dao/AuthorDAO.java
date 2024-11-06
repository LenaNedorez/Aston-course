package ru.nedorezova.dao;

import ru.nedorezova.model.Author;

import java.util.List;


/**
 * Data Access Object (DAO) interface for managing author data.
 * This interface defines methods for retrieving and creating information about authors.
 */
public interface AuthorDAO {

    List<Author> getAllAuthors();
    public Author getAuthorById(Integer id);
    public void createAuthor(Author author);

}
