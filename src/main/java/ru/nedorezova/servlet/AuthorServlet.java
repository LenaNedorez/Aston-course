package ru.nedorezova.controller;

import ru.nedorezova.mappers.AuthorMapper;
import ru.nedorezova.repository.AuthorDAOImpl;
import ru.nedorezova.model.Author;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@Controller
public class AuthorController {

    private final AuthorDAOImpl authorDAOIml;

    public AuthorController(AuthorDAOImpl authorDAOIml) {
        this.authorDAOIml = authorDAOIml;
    }

    @GetMapping("/authors")
    public String getAllAuthors(Model model) {
        List<Author> listOfAuthors = authorDAOIml.getAllAuthors();
        model.addAttribute("listOfAuthors", listOfAuthors);
        return "list-of-authors";
    }

    @GetMapping("/authors/{id}")
    public String getAuthorById(@PathVariable Integer id, Model model) {
        Author author = authorDAOIml.getAuthorById(id);
        model.addAttribute("author", AuthorMapper.INSTANCE.toDto(author));
        return "author";
    }

    @PostMapping("/authors")
    public String createAuthor(@RequestParam String name, @RequestParam String surname) {
        Author newAuthor = new Author();
        newAuthor.setName(name);
        newAuthor.setSurname(surname);
        authorDAOIml.createAuthor(newAuthor);
        return "redirect:/authors";
    }
}
