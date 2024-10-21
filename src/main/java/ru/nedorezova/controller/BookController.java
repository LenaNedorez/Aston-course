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
import ru.nedorezova.mappers.AuthorMapper;
import ru.nedorezova.mappers.BookMapper;
import ru.nedorezova.entity.Author;
import ru.nedorezova.entity.Book;
import ru.nedorezova.service.AuthorService;
import ru.nedorezova.service.BookService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;
    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    public BookController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @GetMapping("/books")
    public String getAllBooks(Model model) {
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books.stream()
                .map(BookMapper.INSTANCE::toDto)
                .collect(Collectors.toList()));
        return "books";
    }

    @GetMapping("/books/{id}")
    public String getBookById(@PathVariable Integer id, Model model) {
        Book book = bookService.getBookById(id);
        model.addAttribute("book", BookMapper.INSTANCE.toDto(book));
        return "book";
    }

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

    @GetMapping("/books/byGenre/{genre}")
    public String getBooksByGenre(@PathVariable String genre, Model model) {
        List<Book> books = bookService.getBooksByGenre(genre);
        model.addAttribute("books", books.stream()
                .map(BookMapper.INSTANCE::toDto)
                .collect(Collectors.toList()));
        return "books";
    }
}
