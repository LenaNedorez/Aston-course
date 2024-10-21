package ru.nedorezova.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.nedorezova.entity.Author;
import ru.nedorezova.entity.Book;
import ru.nedorezova.exception.BookNotFoundException;
import ru.nedorezova.repository.BookRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class BookServiceTest {

    private final BookRepository bookRepository = Mockito.mock(BookRepository.class);
    private final BookService bookService = new BookService(bookRepository);

    @Test
    public void getAllBooks_shouldReturnListOfBooks() {
        List<Book> books = Arrays.asList(
                new Book(1, "Book Title 1", "Genre 1", new Author(1, "Author 1", "Surname 1")),
                new Book(2, "Book Title 2", "Genre 2", new Author(2, "Author 2", "Surname 2"))
        );

        when(bookRepository.findAll()).thenReturn(books);
        List<Book> result = bookService.getAllBooks();
        assertEquals(books, result);
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    public void getBookById_shouldReturnBook() throws BookNotFoundException {
        Book book = new Book(1, "Book Title", "Genre", new Author(1, "Author Name", "Author Surname"));
        when(bookRepository.findById(1)).thenReturn(Optional.of(book));
        Book result = bookService.getBookById(1);
        assertEquals(book, result);
        verify(bookRepository, times(1)).findById(1);
    }

    @Test
    public void getBookById_shouldThrowBookNotFoundException() {
        when(bookRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(BookNotFoundException.class, () -> bookService.getBookById(1));
        verify(bookRepository, times(1)).findById(1);
    }

    @Test
    public void createBook_shouldSaveBook() {
        Book book = new Book(1, "Book Title", "Genre", new Author(1, "Author Name", "Author Surname"));
        Book savedBook = new Book(1, "Book Title", "Genre", new Author(1, "Author Name", "Author Surname"));
        when(bookRepository.save(book)).thenReturn(savedBook);
        Book result = bookService.createBook(book);
        assertEquals(savedBook, result);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    public void getBooksByAuthor_shouldReturnListOfBooksByAuthor() {
        Author author = new Author(1, "Author Name", "Author Surname");
        List<Book> books = Arrays.asList(
                new Book(1, "Book Title 1", "Genre 1", author),
                new Book(2, "Book Title 2", "Genre 2", author)
        );
        when(bookRepository.findByAuthor(author)).thenReturn(books);
        List<Book> result = bookService.getBooksByAuthor(author);
        assertEquals(books, result);
        verify(bookRepository, times(1)).findByAuthor(author);
    }

    @Test
    public void getBooksByGenre_shouldReturnListOfBooksByGenre() {
        String genre = "Genre 1";
        List<Book> books = Arrays.asList(
                new Book(1, "Book Title 1", "Genre 1", new Author(1, "Author 1", "Surname 1")),
                new Book(2, "Book Title 2", "Genre 1", new Author(2, "Author 2", "Surname 2"))
        );

        when(bookRepository.findByGenre(genre)).thenReturn(books);
        List<Book> result = bookService.getBooksByGenre(genre);
        assertEquals(books, result);
        verify(bookRepository, times(1)).findByGenre(genre);
    }
}
