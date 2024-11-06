package ru.nedorezova.dto;

import lombok.*;
import ru.nedorezova.entity.Book;
import java.util.List;

/**
 * Data transfer object representing an author.
 *
 * This DTO is used to transfer author information between layers, such as the
 * controller and the service.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AuthorDto {

    private Integer id;
    private String name;
    private String surname;
    private List<Book> bookList;

    public AuthorDto(Integer id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }
}
