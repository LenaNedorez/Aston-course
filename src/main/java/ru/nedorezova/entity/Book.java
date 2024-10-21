package ru.nedorezova.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a book entity.
 *
 * This class represents a book in the system, including its ID, title,
 * author, a list of genres it belongs to and short description.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String genre;

    @ManyToOne
    @JoinColumn(name = "authors_id")
    private Author author;

    @ManyToMany
    @JoinTable(
            name = "book_genres",
            joinColumns = @JoinColumn(name = "books_id"),
            inverseJoinColumns = @JoinColumn(name = "genres_id")
    )
    List<Genre> genres = new ArrayList<>();

    public Book(Integer id, String title, String genre, Author author) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.author = author;
    }

    @Embedded
    private Description description;

    @Embeddable
    public class Description {

        private int numberOfPages;
        private String Description;

    }
}
