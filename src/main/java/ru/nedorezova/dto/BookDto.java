package ru.nedorezova.dto;

import lombok.*;
import ru.nedorezova.entity.Author;
import ru.nedorezova.entity.Genre;

import java.util.List;

/**
 * A data transfer object (DTO) representing a book.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BookDto {

    private Integer id;
    private String title;
    private String genre;
    private Author author;
    List<Genre> genres;

    public BookDto(Integer id, String title, String genre, Author author) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.author = author;
    }
}
