package ru.nedorezova.mappers;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.nedorezova.dto.BookDto;
import ru.nedorezova.entity.Author;
import ru.nedorezova.entity.Book;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-21T17:23:19+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.8 (Oracle Corporation)"
)
@Component
public class BookMapperImpl implements BookMapper {

    @Override
    public BookDto toDto(Book book) {
        if ( book == null ) {
            return null;
        }

        Integer id = null;
        String title = null;
        String genre = null;
        Author author = null;

        BookDto bookDto = new BookDto( id, title, genre, author );

        return bookDto;
    }
}
