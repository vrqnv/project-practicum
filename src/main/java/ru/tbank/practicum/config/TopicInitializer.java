package ru.tbank.practicum.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicInitializer {

    @Bean
    public NewTopic roomEventsTopic() {
        return new NewTopic("room-events", 1, (short) 1);
    }

    @Bean
    public NewTopic temperatureEventsTopic() {
        return new NewTopic("temperature-events", 1, (short) 1);
    }
}