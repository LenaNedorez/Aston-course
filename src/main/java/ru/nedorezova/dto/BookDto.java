package ru.nedorezova.dto;

import lombok.*;
import ru.nedorezova.entity.Author;
import ru.nedorezova.entity.Genre;

import java.util.List;

/**
 * Data transfer object representing a book.
 *
 * This DTO is used to transfer book information between layers, such as the
 * controller and the service.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@RequiredArgsConstructor
public class BookDto {

    private Integer id;
    private String title;
    private String genre;
    private Author author;
    List<Genre> genres;

}
