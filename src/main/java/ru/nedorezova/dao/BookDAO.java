package ru.nedorezova.dao;

import ru.nedorezova.model.Author;
import ru.nedorezova.model.Book;

import java.util.List;

public interface BookDAO {

    List<Book> getAllBooks();
    Book getBookById(int id);
    void createBook(Book book);
    List<Book> getBooksByAuthor(Author author);
    List<Book> getBooksByGenre(String genre);

}
