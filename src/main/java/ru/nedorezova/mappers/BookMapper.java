package ru.nedorezova.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.nedorezova.dto.BookDto;
import ru.nedorezova.model.Book;

/**
 * A Mapper interface for converting Book objects to BookDto objects.
 * This interface uses MapStruct to automatically generate mapping logic.
 */
@Mapper
public interface BookMapper {

    BookMapper  INSTANCE = Mappers.getMapper( BookMapper .class );

    BookDto toDto(Book book);

}
