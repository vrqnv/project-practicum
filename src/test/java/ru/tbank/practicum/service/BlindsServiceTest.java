package ru.tbank.practicum.service;

import ru.tbank.practicum.entity.BlindsEntity;
import ru.tbank.practicum.repository.BlindsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static ru.tbank.practicum.util.AppConstants.DEFAULT_BLINDS_STATE;
import static ru.tbank.practicum.util.BlindsState.CLOSED;
import static ru.tbank.practicum.util.BlindsState.OPEN;
import static ru.tbank.practicum.util.TestData.blindsWithRoomId;

@ExtendWith(MockitoExtension.class)
class BlindsServiceTest {

    @Mock
    private BlindsRepository blindsRepository;

    @InjectMocks
    private BlindsService blindsService;

    @Test
    void setState_whenBlindsExists_shouldUpdateState() {
        Long roomId = 1L;
        String newState = OPEN.getValue();
        BlindsEntity existingBlinds = blindsWithRoomId(roomId);
        existingBlinds.setState(CLOSED.getValue());

        when(blindsRepository.findByRoomId(roomId)).thenReturn(Optional.of(existingBlinds));

        blindsService.setState(roomId, newState);

        assertEquals(newState, existingBlinds.getState());
        verify(blindsRepository, times(1)).save(existingBlinds);
    }

    @Test
    void setState_whenBlindsNotExists_shouldThrowException() {
        Long roomId = 2L;
        String newState = CLOSED.getValue();

        when(blindsRepository.findByRoomId(roomId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> blindsService.setState(roomId, newState));

        assertEquals("Жалюзи не найдены в комнате " + roomId, exception.getMessage());
        verify(blindsRepository, never()).save(any());
    }

    @Test
    void getState_whenBlindsExists_shouldReturnState() {
        Long roomId = 1L;
        BlindsEntity blinds = blindsWithRoomId(roomId);

        when(blindsRepository.findByRoomId(roomId)).thenReturn(Optional.of(blinds));

        String result = blindsService.getState(roomId);

        assertEquals(DEFAULT_BLINDS_STATE, result);
    }

    @Test
    void getState_whenBlindsNotExists_shouldReturnDefault() {
        Long roomId = 999L;
        when(blindsRepository.findByRoomId(roomId)).thenReturn(Optional.empty());

        String result = blindsService.getState(roomId);

        assertEquals(DEFAULT_BLINDS_STATE, result);
    }

    @Test
    void getState_whenBlindsNotFound_shouldReturnDefault() {
        Long roomId = 999L;
        when(blindsRepository.findByRoomId(roomId)).thenReturn(Optional.empty());

        String result = blindsService.getState(roomId);

        assertEquals(DEFAULT_BLINDS_STATE, result);
    }
}