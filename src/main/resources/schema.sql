CREATE TABLE Author (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        surname VARCHAR(255) NOT NULL
);

CREATE TABLE Book (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      title VARCHAR(255) NOT NULL,
                      genre VARCHAR(255) NOT NULL,
                      author_id INT,
                      FOREIGN KEY (author_id) REFERENCES Author(id)
);

CREATE TABLE Genre (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(255) NOT NULL
);

CREATE TABLE BookGenre (
                           book_id INT,
                           genre_id INT,
                           PRIMARY KEY (book_id, genre_id),
                           FOREIGN KEY (book_id) REFERENCES Book(id),
                           FOREIGN KEY (genre_id) REFERENCES Genre(id)
);