package ru.tbank.practicum.service;

import ru.tbank.practicum.entity.BatteryEntity;
import ru.tbank.practicum.kafka.EventProducer;
import ru.tbank.practicum.metrics.MetricsService;
import ru.tbank.practicum.repository.BatteryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static ru.tbank.practicum.util.AppConstants.DEFAULT_TEMPERATURE;

@ExtendWith(MockitoExtension.class)
class BatteryServiceTest {

    @Mock
    private BatteryRepository batteryRepository;

    @Mock
    private EventProducer eventProducer;

    @Mock
    private MetricsService metricsService;

    @InjectMocks
    private BatteryService batteryService;

    @Test
    void setTemperature_whenBatteryExists_shouldUpdateTemperature() {
        Long roomId = 1L;
        int newTemperature = 25;
        BatteryEntity existingBattery = new BatteryEntity();
        existingBattery.setRoomId(roomId);
        existingBattery.setTemperature(DEFAULT_TEMPERATURE);

        when(batteryRepository.findByRoomId(roomId)).thenReturn(Optional.of(existingBattery));

        batteryService.setTemperature(roomId, newTemperature);

        assertEquals(newTemperature, existingBattery.getTemperature());
        verify(batteryRepository, times(1)).save(existingBattery);
        verify(eventProducer, times(1)).send(eq("temperature-events"), anyString());
        verify(metricsService, times(1)).incrementTemperatureChanges();
    }

    @Test
    void getTemperature_whenBatteryExists_shouldReturnTemperature() {
        Long roomId = 1L;
        BatteryEntity battery = new BatteryEntity();
        battery.setRoomId(roomId);
        battery.setTemperature(DEFAULT_TEMPERATURE);

        when(batteryRepository.findByRoomId(roomId)).thenReturn(Optional.of(battery));

        int result = batteryService.getTemperature(roomId);

        assertEquals(DEFAULT_TEMPERATURE, result);
    }

    @Test
    void getTemperature_whenBatteryNotFound_shouldReturnDefault() {
        Long roomId = 999L;
        when(batteryRepository.findByRoomId(roomId)).thenReturn(Optional.empty());

        int result = batteryService.getTemperature(roomId);

        assertEquals(DEFAULT_TEMPERATURE, result);
    }
}