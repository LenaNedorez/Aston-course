package ru.nedorezova.model;

import java.util.List;

/**
 * Represents a genre.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "authors")
public class Genre {

    private int id;
    private String name;
    private List<Book> books;

}
