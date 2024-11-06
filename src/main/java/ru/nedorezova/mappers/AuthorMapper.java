package ru.nedorezova.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.nedorezova.dto.AuthorDto;
import ru.nedorezova.model.Author;

/**
 * A Mapper interface for converting Author objects to AuthorDto objects.
 * This interface uses MapStruct to automatically generate mapping logic.
 */
@Mapper
public interface AuthorMapper {

    AuthorMapper  INSTANCE = Mappers.getMapper( AuthorMapper .class );

    AuthorDto toDto(Author author);

}
