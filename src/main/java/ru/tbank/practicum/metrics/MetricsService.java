package ru.tbank.practicum.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.stereotype.Service;

@Service
public class MetricsService {

    private final MeterRegistry meterRegistry;
    private final Counter roomsCreated;
    private final Counter temperatureChanges;
    private final Counter kafkaMessagesProduced;
    private final Counter kafkaMessagesProduceErrors;
    private final Timer roomCreationTimer;

    public MetricsService(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        this.roomsCreated = Counter.builder("rooms.created")
                .description("Total rooms created")
                .register(meterRegistry);

        this.temperatureChanges = Counter.builder("temperature.changes")
                .description("Total temperature changes")
                .register(meterRegistry);

        this.kafkaMessagesProduced = Counter.builder("kafka.messages.produced")
                .description("Total Kafka messages produced")
                .register(meterRegistry);

        this.kafkaMessagesProduceErrors = Counter.builder("kafka.messages.produce.errors")
                .description("Total Kafka produce errors")
                .register(meterRegistry);

        this.roomCreationTimer = Timer.builder("rooms.creation.time")
                .description("Time to create room")
                .register(meterRegistry);
    }

    public void incrementRoomsCreated() {
        roomsCreated.increment();
    }

    public void incrementTemperatureChanges() {
        temperatureChanges.increment();
    }

    public void incrementKafkaProduced() {
        kafkaMessagesProduced.increment();
    }

    public void incrementKafkaProduceErrors() {
        kafkaMessagesProduceErrors.increment();
    }

    public void incrementKafkaConsumed(String topic) {
        meterRegistry.counter("kafka.messages.consumed", "topic", topic).increment();
    }

    public void recordRoomCreationTime(Runnable action) {
        roomCreationTimer.record(action);
    }
}