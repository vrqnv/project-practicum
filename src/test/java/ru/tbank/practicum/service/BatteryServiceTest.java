package ru.tbank.practicum.service;

import ru.tbank.practicum.entity.BatteryEntity;
import ru.tbank.practicum.kafka.EventProducer;
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
import static ru.tbank.practicum.util.TestData.batteryWithRoomId;

@ExtendWith(MockitoExtension.class)
class BatteryServiceTest {

    @Mock
    private BatteryRepository batteryRepository;

    @Mock
    private EventProducer eventProducer;

    @InjectMocks
    private BatteryService batteryService;

    @Test
    void setTemperature_whenBatteryExists_shouldUpdateTemperature() {
        Long roomId = 1L;
        int newTemperature = 25;
        BatteryEntity existingBattery = batteryWithRoomId(roomId);
        existingBattery.setTemperature(DEFAULT_TEMPERATURE);

        when(batteryRepository.findByRoomId(roomId)).thenReturn(Optional.of(existingBattery));

        batteryService.setTemperature(roomId, newTemperature);

        assertEquals(newTemperature, existingBattery.getTemperature());
        verify(batteryRepository, times(1)).save(existingBattery);
        verify(eventProducer, times(1)).send(eq("temperature-events"), anyString());
    }

    @Test
    void setTemperature_whenBatteryNotExists_shouldThrowException() {
        Long roomId = 2L;
        int newTemperature = 25;

        when(batteryRepository.findByRoomId(roomId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> batteryService.setTemperature(roomId, newTemperature));

        assertEquals("Батарея не найдена в комнате " + roomId, exception.getMessage());
        verify(batteryRepository, never()).save(any());
        verify(eventProducer, never()).send(any(), any());
    }

    @Test
    void getTemperature_whenBatteryExists_shouldReturnTemperature() {
        Long roomId = 1L;
        BatteryEntity battery = batteryWithRoomId(roomId);

        when(batteryRepository.findByRoomId(roomId)).thenReturn(Optional.of(battery));

        int result = batteryService.getTemperature(roomId);

        assertEquals(DEFAULT_TEMPERATURE, result);
    }

    @Test
    void getTemperature_whenBatteryNotExists_shouldReturnDefault() {
        Long roomId = 999L;
        when(batteryRepository.findByRoomId(roomId)).thenReturn(Optional.empty());

        int result = batteryService.getTemperature(roomId);

        assertEquals(DEFAULT_TEMPERATURE, result);
    }
}