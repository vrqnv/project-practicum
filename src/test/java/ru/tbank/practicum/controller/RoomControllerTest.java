package ru.tbank.practicum.controller;

import ru.tbank.practicum.dto.RoomDto;
import ru.tbank.practicum.entity.RoomEntity;
import ru.tbank.practicum.mapper.RoomMapper;
import ru.tbank.practicum.service.BatteryService;
import ru.tbank.practicum.service.BlindsService;
import ru.tbank.practicum.service.CurtainService;
import ru.tbank.practicum.service.RoomService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.tbank.practicum.util.TestData.randomRoom;

@WebMvcTest(RoomController.class)
class RoomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RoomService roomService;

    @MockitoBean
    private RoomMapper roomMapper;

    @MockitoBean
    private BatteryService batteryService;

    @MockitoBean
    private BlindsService blindsService;

    @MockitoBean
    private CurtainService curtainService;

    @Test
    void getAllRooms_shouldReturnListOfRooms() throws Exception {
        RoomEntity room1 = randomRoom();
        RoomEntity room2 = randomRoom();
        RoomDto dto1 = new RoomDto(room1.getId(), room1.getName());
        RoomDto dto2 = new RoomDto(room2.getId(), room2.getName());

        when(roomService.getAllRooms()).thenReturn(List.of(room1, room2));
        when(roomMapper.toDto(room1)).thenReturn(dto1);
        when(roomMapper.toDto(room2)).thenReturn(dto2);

        mockMvc.perform(get("/api/rooms"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(room1.getId()))
                .andExpect(jsonPath("$[0].name").value(room1.getName()))
                .andExpect(jsonPath("$[1].id").value(room2.getId()))
                .andExpect(jsonPath("$[1].name").value(room2.getName()));

        verify(roomService, times(1)).getAllRooms();
        verify(roomMapper, times(1)).toDto(room1);
        verify(roomMapper, times(1)).toDto(room2);
    }

    @Test
    void createRoom_shouldReturnCreatedRoom() throws Exception {
        String roomName = "Кабинет";
        RoomEntity savedRoom = randomRoom();
        savedRoom.setName(roomName);
        RoomDto savedDto = new RoomDto(savedRoom.getId(), savedRoom.getName());

        when(roomService.createRoom(roomName)).thenReturn(savedRoom);
        when(roomMapper.toDto(savedRoom)).thenReturn(savedDto);

        mockMvc.perform(post("/api/rooms?name=" + roomName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedRoom.getId()))
                .andExpect(jsonPath("$.name").value(roomName));

        verify(roomService, times(1)).createRoom(roomName);
        verify(roomMapper, times(1)).toDto(savedRoom);
    }

    @Test
    void getRoomById_whenRoomExists_shouldReturnRoom() throws Exception {
        RoomEntity room = randomRoom();
        RoomDto dto = new RoomDto(room.getId(), room.getName());

        when(roomService.getRoomById(room.getId())).thenReturn(room);
        when(roomMapper.toDto(room)).thenReturn(dto);

        mockMvc.perform(get("/api/rooms/{id}", room.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(room.getId()))
                .andExpect(jsonPath("$.name").value(room.getName()));

        verify(roomService, times(1)).getRoomById(room.getId());
        verify(roomMapper, times(1)).toDto(room);
    }

    @Test
    void deleteRoom_shouldReturnSuccessMessage() throws Exception {
        Long roomId = 1L;
        doNothing().when(roomService).deleteRoom(roomId);

        mockMvc.perform(delete("/api/rooms/{id}", roomId))
                .andExpect(status().isOk())
                .andExpect(content().string("Комната с id=" + roomId + " удалена вместе со всеми устройствами"));

        verify(roomService, times(1)).deleteRoom(roomId);
    }
}