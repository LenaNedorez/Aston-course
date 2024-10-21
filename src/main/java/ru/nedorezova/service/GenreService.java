package ru.nedorezova.service;

import org.springframework.stereotype.Service;
import ru.nedorezova.exception.BookNotFoundException;
import ru.nedorezova.exception.GenreNotFoundException;
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

    public Genre getGenreById(Integer id) throws GenreNotFoundException { return genreRepository.findById(id)
            .orElseThrow(() -> new GenreNotFoundException("Genre not found"));
    }
}
