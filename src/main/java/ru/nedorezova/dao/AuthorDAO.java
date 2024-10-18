package ru.nedorezova.repository;

/**
 * Data Access Object (DAO) interface for managing author data.
 * This interface defines methods for retrieving and creating information about authors.
 */
@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
}
