package org.example.config.kafka;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

@ConfigurationProperties(prefix = "spring.kafka")
@Component
@Getter
@Setter
public class KafkaConfigProps {
    private Map<String, String> additionalConfigs = Collections.emptyMap();
    private String bootstrapServers;
    private String groupId;
}
