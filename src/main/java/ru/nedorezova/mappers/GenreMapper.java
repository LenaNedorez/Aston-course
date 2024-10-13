package ru.nedorezova.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.nedorezova.dto.GenreDto;
import ru.nedorezova.model.Genre;

@Mapper
public interface GenreMapper {

    GenreMapper  INSTANCE = Mappers.getMapper( GenreMapper .class );

    GenreDto toDto(Genre genre);
}
