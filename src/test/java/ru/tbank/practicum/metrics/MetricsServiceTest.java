package ru.tbank.practicum.metrics;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class MetricsServiceTest {

    private MetricsService metricsService;

    @BeforeEach
    void setUp() {
        metricsService = new MetricsService(new SimpleMeterRegistry());
    }

    @Test
    void incrementRoomsCreated_shouldNotThrow() {
        assertDoesNotThrow(() -> metricsService.incrementRoomsCreated());
    }

    @Test
    void incrementTemperatureChanges_shouldNotThrow() {
        assertDoesNotThrow(() -> metricsService.incrementTemperatureChanges());
    }

    @Test
    void recordRoomCreationTime_shouldNotThrow() {
        assertDoesNotThrow(() -> metricsService.recordRoomCreationTime(() -> {}));
    }
}