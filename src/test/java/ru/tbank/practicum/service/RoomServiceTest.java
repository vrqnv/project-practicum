package ru.tbank.practicum.service;

import ru.tbank.practicum.entity.RoomEntity;
import ru.tbank.practicum.kafka.EventProducer;
import ru.tbank.practicum.metrics.MetricsService;
import ru.tbank.practicum.repository.BatteryRepository;
import ru.tbank.practicum.repository.BlindsRepository;
import ru.tbank.practicum.repository.RoomRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoomServiceTest {

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private BatteryRepository batteryRepository;

    @Mock
    private BlindsRepository blindsRepository;

    @Mock
    private EventProducer eventProducer;

    @Mock
    private MetricsService metricsService;

    @InjectMocks
    private RoomService roomService;

    @Test
    void createRoom_shouldSaveRoomAndSendEvent() {
        String roomName = "Тестовая комната";
        RoomEntity savedRoom = new RoomEntity();
        savedRoom.setId(1L);
        savedRoom.setName(roomName);

        when(roomRepository.save(any(RoomEntity.class))).thenReturn(savedRoom);

        RoomEntity result = roomService.createRoom(roomName);

        assertNotNull(result);
        assertEquals(roomName, result.getName());

        verify(roomRepository, times(1)).save(any(RoomEntity.class));
        verify(eventProducer, times(1)).send(eq("room-events"), anyString());
        verify(metricsService, times(1)).incrementRoomsCreated();
    }
}