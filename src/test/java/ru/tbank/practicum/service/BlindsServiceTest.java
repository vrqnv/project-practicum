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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BlindsServiceTest {

    @Mock
    private BlindsRepository blindsRepository;

    @InjectMocks
    private BlindsService blindsService;

    @Test
    void setState_whenBlindsExists_shouldUpdateState() {
        Long roomId = 1L;
        String newState = "open";
        BlindsEntity existingBlinds = new BlindsEntity();
        existingBlinds.setRoomId(roomId);
        existingBlinds.setState("closed");

        when(blindsRepository.findByRoomId(roomId)).thenReturn(Optional.of(existingBlinds));

        blindsService.setState(roomId, newState);

        assertEquals(newState, existingBlinds.getState());
        verify(blindsRepository, times(1)).save(existingBlinds);
    }

    @Test
    void setState_whenBlindsNotExists_shouldCreateNew() {
        Long roomId = 2L;
        String newState = "closed";

        when(blindsRepository.findByRoomId(roomId)).thenReturn(Optional.empty());

        blindsService.setState(roomId, newState);

        verify(blindsRepository, times(1)).save(any(BlindsEntity.class));
    }

    @Test
    void getState_whenBlindsExists_shouldReturnState() {
        Long roomId = 1L;
        BlindsEntity blinds = new BlindsEntity();
        blinds.setRoomId(roomId);
        blinds.setState("open");

        when(blindsRepository.findByRoomId(roomId)).thenReturn(Optional.of(blinds));

        String result = blindsService.getState(roomId);

        assertEquals("open", result);
    }

    @Test
    void getState_whenBlindsNotExists_shouldReturnDefault() {
        Long roomId = 999L;
        when(blindsRepository.findByRoomId(roomId)).thenReturn(Optional.empty());

        String result = blindsService.getState(roomId);

        assertEquals("closed", result);
    }
}