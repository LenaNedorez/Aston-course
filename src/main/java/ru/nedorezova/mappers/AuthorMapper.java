package ru.nedorezova.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.nedorezova.dto.AuthorDto;
import ru.nedorezova.model.Author;

@Mapper
public interface AuthorMapper {

    AuthorMapper  INSTANCE = Mappers.getMapper( AuthorMapper .class );

    AuthorDto toDto(Author author);

}
