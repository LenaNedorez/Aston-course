package ru.nedorezova.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.nedorezova.dto.BookDto;
import ru.nedorezova.model.Book;

@Mapper
public interface BookMapper {

    BookMapper  INSTANCE = Mappers.getMapper( BookMapper .class );

    BookDto toDto(Book book);

}
