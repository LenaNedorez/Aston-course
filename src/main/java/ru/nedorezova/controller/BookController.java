package ru.nedorezova.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.nedorezova.exception.AuthorNotFoundException;
import ru.nedorezova.exception.BookNotFoundException;
import ru.nedorezova.mappers.AuthorMapper;
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
@Controller
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
    @Autowired
    public BookController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    /**
     * Gets a list of all books and adds it to the model.
     *
     * @param model The model to add the books to.
     * @return The name of the view to render.
     */
    @GetMapping("/books")
    public String getAllBooks(Model model) {
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books.stream()
                .map(BookMapper.INSTANCE::toDto)
                .collect(Collectors.toList()));
        return "books";
    }


    /**
     * Gets a book by ID and adds it to the model.
     *
     * @param id   The ID of the book to retrieve.
     * @param model The model to add the book to.
     * @return The name of the view to render.
     */
    @GetMapping("/books/{id}")
    public String getBookById(@PathVariable Integer id, Model model) {
        Book book = null;
        try {
            book = bookService.getBookById(id);
        } catch (BookNotFoundException e) {
            logger.error("Error fetching book with ID: {}", id, e);
        }
        model.addAttribute("book", BookMapper.INSTANCE.toDto(book));
        return "book";
    }

    /**
     * Creates a new book and redirects to the list of books.
     *
     * @param title   The title of the new book.
     * @param genre   The genre of the new book.
     * @param authorId The ID of the author of the new book.
     * @return The redirect URL.
     */
    @PostMapping("/books")
    public String createBook(@RequestParam String title,
                             @RequestParam String genre,
                             @RequestParam Integer authorId) {
        Author author = null;
        try {
            author = authorService.getAuthorById(authorId);
        } catch (AuthorNotFoundException e) {
            logger.error("Error fetching author with ID: {}", authorId, e);
        }
        Book newBook = new Book();
        newBook.setTitle(title);
        newBook.setGenre(genre);
        newBook.setAuthor(author);
        bookService.createBook(newBook);
        return "redirect:/books";
    }

    /**
     * Gets a list of books by author ID and adds it to the model.
     *
     * @param authorId The ID of the author.
     * @param model    The model to add the books to.
     * @return The name of the view to render.
     */
    @GetMapping("/books/byAuthor/{authorId}")
    public String getBooksByAuthor(@PathVariable Integer authorId, Model model) {
        Author author = null;
        try {
            author = authorService.getAuthorById(authorId);
        } catch (AuthorNotFoundException e) {
            logger.error("Error fetching author with ID: {}", authorId, e);
        }
        List<Book> books = bookService.getBooksByAuthor(author);
        model.addAttribute("books", books.stream()
                .map(BookMapper.INSTANCE::toDto)
                .collect(Collectors.toList()));
        model.addAttribute("author", AuthorMapper.INSTANCE.toDto(author));
        return "books";
    }

    /**
     * Gets a list of books by genre and adds it to the model.
     *
     * @param genre The genre of the books to retrieve.
     * @param model The model to add the books to.
     * @return The name of the view to render.
     */
    @GetMapping("/books/byGenre/{genre}")
    public String getBooksByGenre(@PathVariable String genre, Model model) {
        List<Book> books = bookService.getBooksByGenre(genre);
        model.addAttribute("books", books.stream()
                .map(BookMapper.INSTANCE::toDto)
                .collect(Collectors.toList()));
        return "books";
    }

    /**
     * Gets a list of books by title and adds it to the model.
     *
     * @param title The title of the books to retrieve.
     * @param model The model to add the books to.
     * @return The name of the view to render.
     */
    @GetMapping("/books/byTitle/{title}")
    public String getBooksByTitle(@PathVariable String title, Model model) {
        List<Book> books = bookService.getBooksByTitle(title);
        model.addAttribute("books", books.stream()
                .map(BookMapper.INSTANCE::toDto)
                .collect(Collectors.toList()));
        return "books";
    }
}
