package ru.nedorezova.model;

/**
 * Represents a book.
 */
public class Book {
    private Integer id;
    private String title;
    private String genre;
    private Author author;

    public Book(Integer id, String title, String genre, Author author) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.author = author;
    }

    public Book() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
