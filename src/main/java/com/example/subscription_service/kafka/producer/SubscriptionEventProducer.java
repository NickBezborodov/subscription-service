package com.example.subscription_service.kafka.producer;

import com.example.subscription_service.dto.SubscriptionChangeEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SubscriptionEventProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Value("${app.kafka.topics.subscription-changed}")
    private String outputTopic;

    public void sendSubscriptionChangeEvent(SubscriptionChangeEvent event) {
        try {
            String message = objectMapper.writeValueAsString(event);
            kafkaTemplate.send(outputTopic, event.login(), message);
            log.info("📤 Отправлено событие об изменении подписки: {} ({} → {})",
                    event.login(), event.oldType(), event.newType());
        } catch (Exception e) {
            log.error("❌ Ошибка отправки события для {}: {}", event.login(), e.getMessage());
        }
    }
}