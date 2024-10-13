package ru.nedorezova.mappers;

import javax.annotation.Generated;
import ru.nedorezova.dto.GenreDto;
import ru.nedorezova.model.Genre;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-13T17:22:45+0300",
    comments = "version: 1.6.2, compiler: javac, environment: Java 17.0.8 (Oracle Corporation)"
)
public class GenreMapperImpl implements GenreMapper {

    @Override
    public GenreDto toDto(Genre genre) {
        if ( genre == null ) {
            return null;
        }

        GenreDto genreDto = new GenreDto();

        return genreDto;
    }
}
