package org.example.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

public interface CalculateEnergyLevelService {

    void calculateEnergyLevel(@Payload String message,
                              @Header(KafkaHeaders.RECEIVED_PARTITION_ID) Integer partition,
                              @Header(KafkaHeaders.OFFSET) Long offset,
                              Acknowledgment ack) throws JsonProcessingException;
}
