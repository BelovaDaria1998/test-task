package org.example.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.QuoteDto;
import org.example.entity.EnergyLevel;
import org.example.repository.EnergyLevelRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CalculateEnergyLevelServiceImpl implements CalculateEnergyLevelService {

    private final EnergyLevelRepository energyLevelRepository;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "${spring.kafka.topic}")
    @Override
    public void calculateEnergyLevel(@Payload String message,
                           @Header(KafkaHeaders.RECEIVED_PARTITION_ID) Integer partition,
                           @Header(KafkaHeaders.OFFSET) Long offset,
                           Acknowledgment ack) throws JsonProcessingException {
        log.info("message = {}, partition: {}, offset: {}", message, partition, offset);
        QuoteDto quoteDto = objectMapper.readValue(message, QuoteDto.class);
        Optional<EnergyLevel> energyLevel = energyLevelRepository.findFirstByIsin(quoteDto.getIsin());
        if (energyLevel.isEmpty()) {
            if (quoteDto.getBid() == null) {
                energyLevelRepository.save(toEnergyLevel(quoteDto.getIsin(), quoteDto.getAsk()));
            }
            else {
                energyLevelRepository.save(toEnergyLevel(quoteDto.getIsin(), quoteDto.getBid()));
            }
        } else {
            if (quoteDto.getBid() != null && quoteDto.getBid().compareTo(energyLevel.get().getValue()) > 0) {
                energyLevel.get().setValue(quoteDto.getBid());
                energyLevelRepository.save(energyLevel.get());
            } else if (quoteDto.getAsk().compareTo(energyLevel.get().getValue()) < 0) {
                energyLevel.get().setValue(quoteDto.getAsk());
                energyLevelRepository.save(energyLevel.get());
            }
        }
    }

    private EnergyLevel toEnergyLevel(String isin, BigDecimal value) {
        EnergyLevel energyLevel = new EnergyLevel();
        energyLevel.setIsin(isin);
        energyLevel.setValue(value);
        return energyLevel;
    }
}
