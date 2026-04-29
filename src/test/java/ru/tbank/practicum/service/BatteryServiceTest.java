package ru.tbank.practicum.service;

import ru.tbank.practicum.entity.BatteryEntity;
import ru.tbank.practicum.repository.BatteryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BatteryServiceTest {

    @Mock
    private BatteryRepository batteryRepository;

    @InjectMocks
    private BatteryService batteryService;

    @Test
    void setTemperature_whenBatteryExists_shouldUpdateTemperature() {
        Long roomId = 1L;
        int newTemperature = 25;
        BatteryEntity existingBattery = new BatteryEntity();
        existingBattery.setRoomId(roomId);
        existingBattery.setTemperature(22);

        when(batteryRepository.findByRoomId(roomId)).thenReturn(Optional.of(existingBattery));

        batteryService.setTemperature(roomId, newTemperature);

        assertEquals(newTemperature, existingBattery.getTemperature());
        verify(batteryRepository, times(1)).save(existingBattery);
    }

    @Test
    void setTemperature_whenBatteryNotExists_shouldCreateNew() {
        Long roomId = 2L;
        int newTemperature = 25;

        when(batteryRepository.findByRoomId(roomId)).thenReturn(Optional.empty());

        batteryService.setTemperature(roomId, newTemperature);

        verify(batteryRepository, times(1)).save(any(BatteryEntity.class));
    }

    @Test
    void getTemperature_whenBatteryExists_shouldReturnTemperature() {
        Long roomId = 1L;
        BatteryEntity battery = new BatteryEntity();
        battery.setRoomId(roomId);
        battery.setTemperature(22);

        when(batteryRepository.findByRoomId(roomId)).thenReturn(Optional.of(battery));

        int result = batteryService.getTemperature(roomId);

        assertEquals(22, result);
    }

    @Test
    void getTemperature_whenBatteryNotExists_shouldReturnDefault() {
        Long roomId = 999L;
        when(batteryRepository.findByRoomId(roomId)).thenReturn(Optional.empty());

        int result = batteryService.getTemperature(roomId);

        assertEquals(22, result);
    }
}