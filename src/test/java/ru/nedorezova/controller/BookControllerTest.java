package ru.nedorezova.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.nedorezova.dto.BookDto;
import ru.nedorezova.entity.Author;
import ru.nedorezova.entity.Book;
import ru.nedorezova.exception.AuthorNotFoundException;
import ru.nedorezova.exception.BookNotFoundException;
import ru.nedorezova.service.AuthorService;
import ru.nedorezova.service.BookService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

    @Mock
    private BookService bookService;

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private BookController bookController;

    @Test
    void getAllBooks() {
        List<Book> books = Arrays.asList(new Book(), new Book());
        List<BookDto> bookDtos = Arrays.asList(new BookDto(), new BookDto());

        when(bookService.getAllBooks()).thenReturn(books);

        ResponseEntity<List<BookDto>> response = bookController.getAllBooks();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(bookDtos.size(), response.getBody().size());
        verify(bookService, times(1)).getAllBooks();
    }

    @Test
    void getBookById_found() throws BookNotFoundException {
        Book book = new Book();
        book.setId(1);

        when(bookService.getBookById(1)).thenReturn(book);

        ResponseEntity<BookDto> response = bookController.getBookById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(bookService, times(1)).getBookById(1);
    }

    @Test
    void getBookById_notFound() throws BookNotFoundException {
        when(bookService.getBookById(1)).thenThrow(new BookNotFoundException("Book with id 1 not found"));

        ResponseEntity<BookDto> response = bookController.getBookById(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(bookService, times(1)).getBookById(1);
    }

    @Test
    void createBook() throws AuthorNotFoundException {
        Author author = new Author();
        author.setId(1);
        BookDto bookDto = new BookDto();
        bookDto.setTitle("Test Title");
        bookDto.setGenre("Test Genre");
        bookDto.setAuthor(author);

        when(authorService.getAuthorById(1)).thenReturn(author);

        ResponseEntity<Void> response = bookController.createBook(1, bookDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(authorService, times(1)).getAuthorById(1);
        verify(bookService, times(1)).createBook(any(Book.class));
    }


    @Test
    void createBook_authorNotFound() throws AuthorNotFoundException {
        Author author = new Author();
        author.setId(1);
        BookDto bookDto = new BookDto();
        bookDto.setAuthor(author);
        when(authorService.getAuthorById(1)).thenThrow(new AuthorNotFoundException("Author with id 1 not found"));

        ResponseEntity<Void> response = bookController.createBook(1, bookDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(authorService, times(1)).getAuthorById(1);
        verify(bookService, never()).createBook(any(Book.class));
    }

    @Test
    void getBooksByAuthor_found() throws AuthorNotFoundException {
        Author author = new Author();
        author.setId(1);
        List<Book> books = Arrays.asList(new Book(), new Book());
        List<BookDto> bookDtos = Arrays.asList(new BookDto(), new BookDto());

        when(authorService.getAuthorById(1)).thenReturn(author);
        when(bookService.getBooksByAuthor(author)).thenReturn(books);

        ResponseEntity<List<BookDto>> response = bookController.getBooksByAuthor(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(bookDtos.size(), response.getBody().size());
        verify(authorService, times(1)).getAuthorById(1);
        verify(bookService, times(1)).getBooksByAuthor(author);
    }

    @Test
    void getBooksByAuthor_notFound() throws AuthorNotFoundException {
        when(authorService.getAuthorById(1)).thenThrow(new AuthorNotFoundException("Author with id 1 not found"));

        ResponseEntity<List<BookDto>> response = bookController.getBooksByAuthor(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(authorService, times(1)).getAuthorById(1);
    }

    @Test
    void getBooksByGenre() {
        List<Book> books = Arrays.asList(new Book(), new Book());
        List<BookDto> bookDtos = Arrays.asList(new BookDto(), new BookDto());

        when(bookService.getBooksByGenre("Genre")).thenReturn(books);

        ResponseEntity<List<BookDto>> response = bookController.getBooksByGenre("Genre");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(bookDtos.size(), response.getBody().size());
        verify(bookService, times(1)).getBooksByGenre("Genre");

    }

    @Test
    void getBooksByTitle() {
        List<Book> books = Arrays.asList(new Book(), new Book());
        List<BookDto> bookDtos = Arrays.asList(new BookDto(), new BookDto());

        when(bookService.getBooksByTitle("Title")).thenReturn(books);

        ResponseEntity<List<BookDto>> response = bookController.getBooksByTitle("Title");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(bookDtos.size(), response.getBody().size());
        verify(bookService, times(1)).getBooksByTitle("Title");
    }

}