package ru.tbank.practicum.service;

import ru.tbank.practicum.entity.RoomEntity;
import ru.tbank.practicum.kafka.EventProducer;
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
        assertEquals(1L, result.getId());

        verify(roomRepository, times(1)).save(any(RoomEntity.class));
        // Проверяем, что событие отправлено (сейчас в коде 2 вызова)
        verify(eventProducer, atLeast(1)).send(eq("room-events"), anyString());
        verify(batteryRepository, never()).save(any());
        verify(blindsRepository, never()).save(any());
    }

    @Test
    void getRoomById_whenRoomExists_shouldReturnRoom() {
        Long roomId = 1L;
        RoomEntity room = new RoomEntity();
        room.setId(roomId);
        room.setName("Гостиная");

        when(roomRepository.findById(roomId)).thenReturn(Optional.of(room));

        RoomEntity result = roomService.getRoomById(roomId);

        assertNotNull(result);
        assertEquals(roomId, result.getId());
        assertEquals("Гостиная", result.getName());
    }

    @Test
    void getRoomById_whenRoomNotFound_shouldThrowException() {
        Long roomId = 999L;
        when(roomRepository.findById(roomId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> roomService.getRoomById(roomId));

        assertEquals("Комната с id=" + roomId + " не найдена", exception.getMessage());
    }

    @Test
    void getAllRooms_shouldReturnListOfRooms() {
        when(roomRepository.findAll()).thenReturn(java.util.List.of(new RoomEntity(), new RoomEntity()));

        var result = roomService.getAllRooms();

        assertEquals(2, result.size());
        verify(roomRepository, times(1)).findAll();
    }
}