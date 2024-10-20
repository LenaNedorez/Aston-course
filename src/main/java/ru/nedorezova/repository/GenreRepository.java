package ru.nedorezova.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nedorezova.entity.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Integer> {
}
