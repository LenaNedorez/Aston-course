package ru.nedorezova.service;

import org.springframework.stereotype.Service;
import ru.nedorezova.repository.BookRepository;
import ru.nedorezova.entity.Author;
import ru.nedorezova.entity.Book;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Integer id) {
        return bookRepository.findById(id).orElse(null);
    }

    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    public List<Book> getBooksByAuthor(Author author) {
        return bookRepository.findByAuthor(author);
    }

    public List<Book> getBooksByGenre(String genre) {
        return bookRepository.findByGenre(genre);
    }
}
