package ru.nedorezova.dao;

import ru.nedorezova.model.Author;
import ru.nedorezova.model.Book;

import java.util.List;

/**
 * Data Access Object (DAO) interface for managing book data.
 * This interface defines methods for retrieving and creating information about books.
 */
public interface BookDAO {

    List<Book> getAllBooks();
    Book getBookById(Integer id);
    void createBook(Book book);
    List<Book> getBooksByAuthor(Author author);
    List<Book> getBooksByGenre(String genre);

}
