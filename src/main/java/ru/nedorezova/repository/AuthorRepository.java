package ru.nedorezova.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nedorezova.model.Author;

import java.util.List;


@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
}
