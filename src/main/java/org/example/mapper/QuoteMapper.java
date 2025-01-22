package org.example.mapper;

import org.example.dto.QuoteDto;
import org.example.entity.Quote;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface QuoteMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    Quote toEntity(QuoteDto quoteDto);

    QuoteDto toDto(Quote quote);
}
