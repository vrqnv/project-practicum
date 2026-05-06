package ru.tbank.practicum.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class EventConsumer {

    private static final Logger log = LoggerFactory.getLogger(EventConsumer.class);

    @KafkaListener(topics = "room-events", groupId = "my-app-group")
    public void consumeRoomEvent(String message) {
        log.info("Получено сообщение из топика room-events: {}", message);
    }

    @KafkaListener(topics = "temperature-events", groupId = "my-app-group")
    public void consumeTemperatureEvent(String message) {
        log.info("Получено сообщение из топика temperature-events: {}", message);
    }
}