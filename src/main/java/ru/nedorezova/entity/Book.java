package ru.nedorezova.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

}
