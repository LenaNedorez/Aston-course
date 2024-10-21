package ru.nedorezova.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.Model;
import ru.nedorezova.entity.Author;
import ru.nedorezova.entity.Book;
import ru.nedorezova.exception.AuthorNotFoundException;
import ru.nedorezova.exception.BookNotFoundException;
import ru.nedorezova.service.AuthorService;
import ru.nedorezova.service.BookService;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class BookControllerTest {

    private final BookService bookService = Mockito.mock(BookService.class);
    private final AuthorService authorService = Mockito.mock(AuthorService.class);
    private final BookController bookController = new BookController(bookService, authorService);

    @Test
    public void getAllBooks_shouldReturnListOfBooks() {
        Author author1 = new Author(1, "Ivan", "Ivanov");
        Author author2 = new Author(2, "Oleg", "Olegov");
        Book book1 = new Book(1, "Book Title 1", "Book Genre 1", author1);
        Book book2 = new Book(2, "Book Title 2", "Book Genre 2", author2);

        when(bookService.getAllBooks()).thenReturn(Arrays.asList(book1, book2));

        Model model = Mockito.mock(Model.class);
        String viewName = bookController.getAllBooks(model);
        verify(bookService, times(1)).getAllBooks();
        assertEquals("books", viewName);
    }

    @Test
    public void getBookById_shouldReturnBook() throws BookNotFoundException {
        Book book = new Book(1, "Book Title", "Genre", new Author(1, "Author Name", "Author Surname"));
        when(bookService.getBookById(1)).thenReturn(book);

        Model model = Mockito.mock(Model.class);

        String viewName = bookController.getBookById(1, model);

        verify(bookService, times(1)).getBookById(1);
        assertEquals("book", viewName);
    }

    @Test
    public void createBook_shouldCreateNewBook() throws AuthorNotFoundException {
        Author author = new Author(1, "Author Name", "Author Surname");
        Book newBook = new Book(null, "New Book Title", "New Genre", author);

        when(authorService.getAuthorById(1)).thenReturn(author);

        String redirectUrl = bookController.createBook("New Book Title", "New Genre", 1);

        verify(authorService, times(1)).getAuthorById(1);
        verify(bookService, times(1)).createBook(any(Book.class));
        assertEquals("redirect:/books", redirectUrl);
    }

    @Test
    public void getBooksByAuthor_shouldReturnListOfBooksByAuthor() throws AuthorNotFoundException {
        Author author = new Author(1, "Author Name", "Author Surname");

        when(authorService.getAuthorById(1)).thenReturn(author);
        when(bookService.getBooksByAuthor(author)).thenReturn(Arrays.asList(
                new Book(1, "Book Title 1", "Genre 1", author),
                new Book(2, "Book Title 2", "Genre 2", author)
        ));

        Model model = Mockito.mock(Model.class);

        String viewName = bookController.getBooksByAuthor(1, model);

        verify(authorService, times(1)).getAuthorById(1);
        verify(bookService, times(1)).getBooksByAuthor(author);
        assertEquals("books", viewName);
    }

    @Test
    public void getBooksByGenre_shouldReturnListOfBooksByGenre() {

        when(bookService.getBooksByGenre("Genre 1")).thenReturn(Arrays.asList(
                new Book(1, "Book Title 1", "Genre 1", new Author(1, "Author 1", "Surname 1")),
                new Book(2, "Book Title 2", "Genre 1", new Author(2, "Author 2", "Surname 2"))
        ));

        Model model = Mockito.mock(Model.class);

        String viewName = bookController.getBooksByGenre("Genre 1", model);

        verify(bookService, times(1)).getBooksByGenre("Genre 1");

        assertEquals("books", viewName);
    }
}
