package ru.nedorezova.mappers;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.nedorezova.dto.BookDto;
import ru.nedorezova.entity.Book;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-20T22:31:44+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.8 (Oracle Corporation)"
)
@Component
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
