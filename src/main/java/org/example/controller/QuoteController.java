package org.example.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.example.dto.QuoteDto;
import org.example.service.QuoteService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class QuoteController {

    private final QuoteService quoteService;

    @PostMapping("/quote")
    public QuoteDto createQuote(@Valid @RequestBody QuoteDto quoteDto) throws JsonProcessingException {
        return quoteService.createQuote(quoteDto);
    }
}
