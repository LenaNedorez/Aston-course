CREATE TABLE Author (
                        id SERIAL PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        surname VARCHAR(255) NOT NULL
);

CREATE TABLE Book (
                      id SERIAL PRIMARY KEY,
                      title VARCHAR(255) NOT NULL,
                      genre VARCHAR(255) NOT NULL,
                      author_id INTEGER REFERENCES Author(id)
);

CREATE TABLE Genre (
                       id SERIAL PRIMARY KEY,
                       name VARCHAR(255) NOT NULL
);

CREATE TABLE BookGenre (
                           book_id INTEGER REFERENCES Book(id),
                           genre_id INTEGER REFERENCES Genre(id),
                           PRIMARY KEY (book_id, genre_id)
);