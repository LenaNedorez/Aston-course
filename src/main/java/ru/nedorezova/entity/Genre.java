package ru.nedorezova.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a genre entity.
 *
 * This class represents a genre in the system, including its ID, name, and a list
 * of books that belong to this genre.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @ManyToMany(mappedBy = "genres")
    private List<Book> books = new ArrayList<>();

    public Genre(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
