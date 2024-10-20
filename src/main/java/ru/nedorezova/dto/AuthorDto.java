package ru.nedorezova.dto;

import lombok.*;
import ru.nedorezova.entity.Book;
import java.util.List;

/**
 * A data transfer object (DTO) representing an author.
 * This class is used to transfer author data between different parts of the application,
 * such as the database, the servlet, and the view layer.
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

}
