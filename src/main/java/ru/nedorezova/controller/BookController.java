package ru.nedorezova.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nedorezova.dto.BookDto;
import ru.nedorezova.exception.AuthorNotFoundException;
import ru.nedorezova.exception.BookNotFoundException;
import ru.nedorezova.mappers.BookMapper;
import ru.nedorezova.entity.Author;
import ru.nedorezova.entity.Book;
import ru.nedorezova.service.AuthorService;
import ru.nedorezova.service.BookService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for managing books.
 */
@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;
    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    /**
     * Constructs a new BookController with the given BookService and AuthorService.
     *
     * @param bookService   The BookService to use.
     * @param authorService The AuthorService to use.
     */
    public BookController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    /**
     * Gets a list of all books and adds it to the model.
     *
     * @return The name of the view to render.
     */
    @GetMapping
    public ResponseEntity<List<BookDto>> getAllBooks() {
        List<BookDto> bookDtos = bookService.getAllBooks().stream()
                .map(BookMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(bookDtos);
    }

    /**
     * Gets a book by ID and adds it to the model.
     *
     * @param id   The ID of the book to retrieve.
     * @return The name of the view to render.
     */
    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable Integer id) {
        try {
            Book book = bookService.getBookById(id);
            return ResponseEntity.ok(BookMapper.INSTANCE.toDto(book));
        } catch (BookNotFoundException e) {
            logger.error("Error fetching book with ID: {}", id, e);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Creates a new book and redirects to the list of books.
     *
     * @param authorId   The id of the author of the new book.
     * @param bookDto   The Dto of the new book.
     * @return The redirect URL.
     */
    @PostMapping
    public ResponseEntity<Void> createBook(@RequestParam Integer authorId, @RequestBody BookDto bookDto) {
        Author author = null;
        try {
            author = authorService.getAuthorById(authorId);
        } catch (AuthorNotFoundException e) {
            logger.error("Error fetching author with ID: {}", authorId, e);
            return ResponseEntity.badRequest().build();
        }
        Book newBook = new Book();
        newBook.setAuthor(author);
        newBook.setTitle(bookDto.getTitle());
        newBook.setGenre(bookDto.getGenre());
        bookService.createBook(newBook);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Gets a list of books by author ID and adds it to the model.
     *
     * @param authorId The ID of the author.
     * @return The name of the view to render.
     */
    @GetMapping("/byAuthor/{authorId}")
    public ResponseEntity<List<BookDto>> getBooksByAuthor(@PathVariable Integer authorId) {
        try {
            Author author = authorService.getAuthorById(authorId);
            List<BookDto> bookDtos = bookService.getBooksByAuthor(author).stream()
                    .map(BookMapper.INSTANCE::toDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(bookDtos);
        } catch (AuthorNotFoundException e) {
            logger.error("Error fetching author with ID: {}", authorId, e);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Gets a list of books by genre and adds it to the model.
     *
     * @param genre The genre of the books to retrieve.
     * @return The name of the view to render.
     */
    @GetMapping("/byGenre/{genre}")
    public ResponseEntity<List<BookDto>> getBooksByGenre(@PathVariable String genre) {
        List<BookDto> bookDtos = bookService.getBooksByGenre(genre).stream()
                .map(BookMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(bookDtos);
    }

    /**
     * Gets a list of books by title and adds it to the model.
     *
     * @param title The title of the books to retrieve.
     * @return The name of the view to render.
     */
    @GetMapping("/byTitle/{title}")
    public ResponseEntity<List<BookDto>> getBooksByTitle(@PathVariable String title) {
        List<BookDto> bookDtos = bookService.getBooksByTitle(title).stream()
                .map(BookMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(bookDtos);
    }
}
