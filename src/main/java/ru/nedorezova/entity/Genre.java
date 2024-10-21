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
