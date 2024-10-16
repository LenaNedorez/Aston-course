package ru.nedorezova.mappers;

import javax.annotation.Generated;
import ru.nedorezova.dto.AuthorDto;
import ru.nedorezova.model.Author;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-14T19:22:19+0300",
    comments = "version: 1.6.2, compiler: javac, environment: Java 17.0.8 (Oracle Corporation)"
)
public class AuthorMapperImpl implements AuthorMapper {

    @Override
    public AuthorDto toDto(Author author) {
        if ( author == null ) {
            return null;
        }

        AuthorDto authorDto = new AuthorDto();

        return authorDto;
    }
}
