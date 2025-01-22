package org.example.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.example.dto.QuoteDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.kafka.support.SendResult;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaServiceImpl implements KafkaService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void sendMessage(String topic, QuoteDto quoteDto) throws JsonProcessingException {
        String message = objectMapper.writeValueAsString(quoteDto);
        ProducerRecord<String, String> record = new ProducerRecord<>(topic,
                String.valueOf(System.currentTimeMillis()), message);
        log.info("KEY {} partition {}", record.key(), record.partition());
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(record);
        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.info("PARTITION {}", result.getRecordMetadata().partition());
                log.debug("Pull message=[{}] with offset=[{}] to topic {}", message.length() > 100
                                ? message.substring(0, 100) : message,
                        result.getRecordMetadata().offset(),
                        topic
                );
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error("Unable to send message=[{}] due to :{}", message, ex.getMessage());
            }
        });
    }
}
