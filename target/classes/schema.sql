CREATE TABLE authors (
                        id SERIAL PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        surname VARCHAR(255) NOT NULL
);

CREATE TABLE books (
                      id SERIAL PRIMARY KEY,
                      title VARCHAR(255) NOT NULL,
                      genre VARCHAR(255) NOT NULL,
                      author_id INTEGER REFERENCES authors(id)
);

CREATE TABLE genres (
                       id SERIAL PRIMARY KEY,
                       name VARCHAR(255) NOT NULL
);

CREATE TABLE book_genres (
                           book_id INTEGER REFERENCES books(id),
                           genre_id INTEGER REFERENCES genres(id),
                           PRIMARY KEY (book_id, genre_id)
);