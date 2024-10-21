package ru.nedorezova.mappers;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.nedorezova.dto.GenreDto;
import ru.nedorezova.entity.Genre;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-21T17:23:19+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.8 (Oracle Corporation)"
)
@Component
public class GenreMapperImpl implements GenreMapper {

    @Override
    public GenreDto toDto(Genre genre) {
        if ( genre == null ) {
            return null;
        }

        int id = 0;
        String name = null;

        GenreDto genreDto = new GenreDto( id, name );

        return genreDto;
    }
}
