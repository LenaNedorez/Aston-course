package ru.nedorezova.servlet;

import ru.nedorezova.mappers.AuthorMapper;
import ru.nedorezova.mappers.BookMapper;
import ru.nedorezova.model.Author;
import ru.nedorezova.model.Book;
import ru.nedorezova.repository.AuthorDAOImpl;
import ru.nedorezova.repository.BookDAOImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class BookController {

    private final BookDAOImpl bookDAO;
    private final AuthorDAOImpl authorDAO;

    public BookController(BookDAOImpl bookDAO, AuthorDAOImpl authorDAO) {
        this.bookDAO = bookDAO;
        this.authorDAO = authorDAO;
    }

    @GetMapping("/books")
    public String getAllBooks(Model model) {
        List<Book> books = bookDAO.getAllBooks();
        model.addAttribute("books", books.stream()
                .map(BookMapper.INSTANCE::toDto)
                .collect(Collectors.toList()));
        return "books";
    }

    @GetMapping("/books/{id}")
    public String getBookById(@PathVariable Integer id, Model model) {
        Book book = bookDAO.getBookById(id);
        model.addAttribute("book", BookMapper.INSTANCE.toDto(book));
        return "book";
    }

    @PostMapping("/books")
    public String createBook(@RequestParam String title,
                             @RequestParam String genre,
                             @RequestParam Integer authorId,
                             Model model) {
        Author author = authorDAO.getAuthorById(authorId);
        Book newBook = new Book();
        newBook.setTitle(title);
        newBook.setGenre(genre);
        newBook.setAuthor(author);
        bookDAO.createBook(newBook);
        return "redirect:/books";
    }

    @GetMapping("/books/byAuthor/{authorId}")
    public String getBooksByAuthor(@PathVariable Integer authorId, Model model) {
        Author author = authorDAO.getAuthorById(authorId);
        List<Book> books = bookDAO.getBooksByAuthor(author);
        model.addAttribute("books", books.stream()
                .map(BookMapper.INSTANCE::toDto)
                .collect(Collectors.toList()));
        model.addAttribute("author", AuthorMapper.INSTANCE.toDto(author));
        return "books";
    }

    @GetMapping("/books/byGenre/{genre}")
    public String getBooksByGenre(@PathVariable String genre, Model model) {
        List<Book> books = bookDAO.getBooksByGenre(genre);
        model.addAttribute("books", books.stream()
                .map(BookMapper.INSTANCE::toDto)
                .collect(Collectors.toList()));
        return "books";
    }
}
