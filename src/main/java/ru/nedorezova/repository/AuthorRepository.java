package ru.nedorezova.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nedorezova.entity.Author;


@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
}
