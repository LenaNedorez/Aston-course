package ru.nedorezova.service;

import org.springframework.stereotype.Service;
import ru.nedorezova.repository.GenreRepository;
import ru.nedorezova.entity.Book;
import ru.nedorezova.entity.Genre;

import java.util.List;


@Service
public class GenreService {

    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    public List<Genre> getGenresByBook(Book book) {
        return book.getGenres();
    }
}
