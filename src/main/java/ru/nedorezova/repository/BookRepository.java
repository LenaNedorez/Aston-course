package ru.nedorezova.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nedorezova.entity.Author;
import ru.nedorezova.entity.Book;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findByAuthor(Author author);
    List<Book> findByGenre(String genre);
}
