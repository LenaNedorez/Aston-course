package ru.nedorezova.model;

/**
 * Represents a book.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "books")
public class Book {
    
    private Integer id;
    private String title;
    private String genre;
    private Author author;

}
