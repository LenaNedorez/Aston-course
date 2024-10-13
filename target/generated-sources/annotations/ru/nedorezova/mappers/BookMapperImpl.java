package ru.nedorezova.mappers;

import javax.annotation.Generated;
import ru.nedorezova.dto.BookDto;
import ru.nedorezova.model.Book;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-13T17:22:45+0300",
    comments = "version: 1.6.2, compiler: javac, environment: Java 17.0.8 (Oracle Corporation)"
)
public class BookMapperImpl implements BookMapper {

    @Override
    public BookDto toDto(Book book) {
        if ( book == null ) {
            return null;
        }

        BookDto bookDto = new BookDto();

        return bookDto;
    }
}
