package ru.nedorezova.dto;

import lombok.*;
import ru.nedorezova.entity.Author;

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

}
