package ru.tbank.practicum.service;

import ru.tbank.practicum.entity.CurtainScheduleEntity;
import ru.tbank.practicum.repository.CurtainScheduleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static ru.tbank.practicum.util.TestData.curtainScheduleWithRoomId;

@ExtendWith(MockitoExtension.class)
class CurtainServiceTest {

    @Mock
    private CurtainScheduleRepository curtainScheduleRepository;

    @InjectMocks
    private CurtainService curtainService;

    @Test
    void setSchedule_whenScheduleExists_shouldUpdateSchedule() {
        Long roomId = 1L;
        String time = "10:00";
        String action = "close";
        CurtainScheduleEntity existingSchedule = curtainScheduleWithRoomId(roomId, "08:00", "open");

        when(curtainScheduleRepository.findByRoomId(roomId)).thenReturn(Optional.of(existingSchedule));

        curtainService.setSchedule(roomId, time, action);

        assertEquals(LocalTime.parse(time), existingSchedule.getScheduleTime());
        assertEquals(action, existingSchedule.getScheduleAction());
        verify(curtainScheduleRepository, times(1)).save(existingSchedule);
    }

    @Test
    void setSchedule_whenScheduleNotExists_shouldCreateNew() {
        Long roomId = 2L;
        String time = "09:00";
        String action = "open";

        when(curtainScheduleRepository.findByRoomId(roomId)).thenReturn(Optional.empty());

        curtainService.setSchedule(roomId, time, action);

        verify(curtainScheduleRepository, times(1)).save(any(CurtainScheduleEntity.class));
    }

    @Test
    void getScheduleTime_whenScheduleExists_shouldReturnTime() {
        Long roomId = 1L;
        String time = "08:00";
        CurtainScheduleEntity schedule = curtainScheduleWithRoomId(roomId, time, "open");

        when(curtainScheduleRepository.findByRoomId(roomId)).thenReturn(Optional.of(schedule));

        String result = curtainService.getScheduleTime(roomId);

        assertEquals(time, result);
    }

    @Test
    void getScheduleTime_whenScheduleNotExists_shouldReturnNull() {
        Long roomId = 999L;
        when(curtainScheduleRepository.findByRoomId(roomId)).thenReturn(Optional.empty());

        String result = curtainService.getScheduleTime(roomId);

        assertNull(result);
    }

    @Test
    void getScheduleAction_whenScheduleExists_shouldReturnAction() {
        Long roomId = 1L;
        String action = "open";
        CurtainScheduleEntity schedule = curtainScheduleWithRoomId(roomId, "08:00", action);

        when(curtainScheduleRepository.findByRoomId(roomId)).thenReturn(Optional.of(schedule));

        String result = curtainService.getScheduleAction(roomId);

        assertEquals(action, result);
    }

    @Test
    void getScheduleAction_whenScheduleNotExists_shouldReturnNull() {
        Long roomId = 999L;
        when(curtainScheduleRepository.findByRoomId(roomId)).thenReturn(Optional.empty());

        String result = curtainService.getScheduleAction(roomId);

        assertNull(result);
    }
}