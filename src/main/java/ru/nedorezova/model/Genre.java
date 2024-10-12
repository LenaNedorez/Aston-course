package ru.nedorezova.model;

import java.util.List;

public class Genre {

    private int id;
    private String name;
    private List<Book> books;

    public Genre(int id, String name, List<Book> books) {
        this.id = id;
        this.name = name;
        this.books = books;
    }

    public Genre() {
    }

    public Genre(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
