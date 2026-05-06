package ru.tbank.practicum.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.tbank.practicum.metrics.MetricsService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class EventProducer {

    private static final Logger log = LoggerFactory.getLogger(EventProducer.class);
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final MetricsService metricsService;

    public EventProducer(KafkaTemplate<String, Object> kafkaTemplate,
                         MetricsService metricsService) {
        this.kafkaTemplate = kafkaTemplate;
        this.metricsService = metricsService;
    }

    public void send(String topic, Object event) {
        kafkaTemplate.send(topic, event).whenComplete((result, exception) -> {
            if (exception == null) {
                metricsService.incrementKafkaProduced();
                log.info("Отправлено событие в топик '{}': {}", topic, event);
                return;
            }
            metricsService.incrementKafkaProduceErrors();
            log.error("Ошибка отправки события в топик '{}': {}", topic, exception.getMessage(), exception);
        });
    }
}