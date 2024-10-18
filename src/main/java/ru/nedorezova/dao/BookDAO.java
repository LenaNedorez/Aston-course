package ru.nedorezova.repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findByAuthor(Author author);
    List<Book> findByGenre(String genre);
}
