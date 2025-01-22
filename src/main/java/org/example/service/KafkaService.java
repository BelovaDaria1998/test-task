package org.example.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.dto.QuoteDto;

public interface KafkaService {

    void sendMessage(String topic, QuoteDto quoteDto) throws JsonProcessingException;
}
