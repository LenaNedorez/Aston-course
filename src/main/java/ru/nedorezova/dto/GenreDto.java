package ru.nedorezova.dto;

import ru.nedorezova.model.Book;
import java.util.List;

/**
 * A data transfer object (DTO) representing a genre.
 */
public class GenreDto {

    private int id;
    private String name;
    private List<Book> books;

}
