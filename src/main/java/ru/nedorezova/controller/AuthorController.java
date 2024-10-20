package ru.nedorezova.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.nedorezova.mappers.AuthorMapper;
import ru.nedorezova.model.Author;
import ru.nedorezova.service.AuthorService;

import java.util.List;


@Controller
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/authors")
    public String getAllAuthors(Model model) {
        List<Author> listOfAuthors = authorService.getAllAuthors();
        model.addAttribute("listOfAuthors", listOfAuthors);
        return "list-of-authors";
    }

    @GetMapping("/authors/{id}")
    public String getAuthorById(@PathVariable Integer id, Model model) {
        Author author = authorService.getAuthorById(id);
        model.addAttribute("author", AuthorMapper.INSTANCE.toDto(author));
        return "author";
    }

    @PostMapping("/authors")
    public String createAuthor(@RequestParam String name, @RequestParam String surname) {
        Author newAuthor = new Author();
        newAuthor.setName(name);
        newAuthor.setSurname(surname);
        authorService.createAuthor(newAuthor);
        return "redirect:/authors";
    }
}