package org.example.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.example.dto.QuoteDto;
import org.example.entity.Quote;
import org.example.mapper.QuoteMapper;
import org.example.repository.QuoteRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuoteServiceImpl implements QuoteService {

    private final QuoteMapper quoteMapper;
    private final QuoteRepository quoteRepository;
    private final KafkaService kafkaService;

    @Value("${spring.kafka.topic}")
    private String topic;

    @Override
    public QuoteDto createQuote(QuoteDto quoteDto) throws JsonProcessingException {
        Quote quote = quoteRepository.save(quoteMapper.toEntity(quoteDto));
        QuoteDto result = quoteMapper.toDto(quote);
        kafkaService.sendMessage(topic, result);
        return result;
    }
}
