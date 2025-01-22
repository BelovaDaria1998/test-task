package org.example.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.dto.QuoteDto;

public interface QuoteService {

    QuoteDto createQuote(QuoteDto quoteDto) throws JsonProcessingException;
}
