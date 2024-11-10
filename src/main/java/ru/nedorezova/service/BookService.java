package ru.nedorezova.service;

import org.springframework.stereotype.Service;
import ru.nedorezova.exception.BookNotFoundException;
import ru.nedorezova.repository.BookRepository;
import ru.nedorezova.entity.Author;
import ru.nedorezova.entity.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Service for managing books.
 *
 * This service provides operations for retrieving, creating, and searching for
 * books based on author or genre.
 */
@Service
public class BookService {

    private final BookRepository bookRepository;
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Constructs a new BookService with the given BookRepository.
     *
     * @param bookRepository The BookRepository to use.
     */
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * Retrieves a list of all books.
     *
     * @return A list of all books.
     */
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    /**
     * Retrieves a book by its ID.
     *
     * @param id The ID of the book to retrieve.
     * @return The book with the given ID.
     * @throws BookNotFoundException If no book with the given ID exists.
     */
    public Book getBookById(Integer id) throws BookNotFoundException {
        return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Book not found"));
    }

    /**
     * Creates a new book.
     *
     * @param book The book to create.
     * @return The newly created book.
     */
    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    /**
     * Retrieves a list of books written by the given author.
     *
     * @param author The author to search for books by.
     * @return A list of books written by the author.
     */
    public List<Book> getBooksByAuthor(Author author) {
        return bookRepository.findByAuthor(author);
    }

    /**
     * Retrieves a list of books belonging to the given genre.
     *
     * @param genre The genre to search for books by.
     * @return A list of books belonging to the genre.
     */
    public List<Book> getBooksByGenre(String genre) {
        return bookRepository.findByGenre(genre);
    }

    /**
     * Retrieves a list of books with the given title using a JPQL query.
     *
     * @param title The title of the books to retrieve.
     * @return A list of books with the given title.
     */
    public List<Book> getBooksByTitle(String title) {
        TypedQuery<Book> query = entityManager.createQuery(
                "SELECT b FROM Book b WHERE b.title LIKE :title", Book.class);
        query.setParameter("title", "%" + title + "%");
        return query.getResultList();
    }
}
