package ru.nedorezova.dto;

import lombok.*;
import ru.nedorezova.entity.Book;
import java.util.List;


/**
 * A data transfer object (DTO) representing a genre.
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
