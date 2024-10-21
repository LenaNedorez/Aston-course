package ru.nedorezova.dto;

import lombok.*;
import ru.nedorezova.entity.Book;
import java.util.List;


/**
 * Data transfer object representing a genre.
 *
 * This DTO is used to transfer information about book genre between layers, such as the
 * controller and the service.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GenreDto {

    private int id;
    private String name;
    private List<Book> books;

    public GenreDto(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
