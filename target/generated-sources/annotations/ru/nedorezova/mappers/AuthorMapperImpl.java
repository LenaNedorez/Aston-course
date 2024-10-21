package ru.nedorezova.mappers;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.nedorezova.dto.AuthorDto;
import ru.nedorezova.entity.Author;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-21T17:23:19+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.8 (Oracle Corporation)"
)
@Component
public class AuthorMapperImpl implements AuthorMapper {

    @Override
    public AuthorDto toDto(Author author) {
        if ( author == null ) {
            return null;
        }

        Integer id = null;
        String name = null;
        String surname = null;

        AuthorDto authorDto = new AuthorDto( id, name, surname );

        return authorDto;
    }
}
