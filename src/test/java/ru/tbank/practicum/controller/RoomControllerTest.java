package ru.tbank.practicum.controller;

import ru.tbank.practicum.dto.RoomDto;
import ru.tbank.practicum.entity.RoomEntity;
import ru.tbank.practicum.mapper.RoomMapper;
import ru.tbank.practicum.service.RoomService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RoomController.class)
class RoomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RoomService roomService;

    @MockitoBean
    private RoomMapper roomMapper;

    @Test
    void getAllRooms_shouldReturnListOfRooms() throws Exception {
        RoomEntity room1 = new RoomEntity(1L, "Гостиная");
        RoomEntity room2 = new RoomEntity(2L, "Спальня");
        RoomDto dto1 = new RoomDto(1L, "Гостиная");
        RoomDto dto2 = new RoomDto(2L, "Спальня");

        when(roomService.getAllRooms()).thenReturn(List.of(room1, room2));
        when(roomMapper.toDto(room1)).thenReturn(dto1);
        when(roomMapper.toDto(room2)).thenReturn(dto2);

        mockMvc.perform(get("/api/rooms"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Гостиная"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Спальня"));

        verify(roomService, times(1)).getAllRooms();
        verify(roomMapper, times(1)).toDto(room1);
        verify(roomMapper, times(1)).toDto(room2);
    }

    @Test
    void createRoom_shouldReturnCreatedRoom() throws Exception {
        String roomName = "Кабинет";
        RoomEntity savedRoom = new RoomEntity(3L, roomName);
        RoomDto savedDto = new RoomDto(3L, roomName);

        when(roomService.createRoom(roomName)).thenReturn(savedRoom);
        when(roomMapper.toDto(savedRoom)).thenReturn(savedDto);

        mockMvc.perform(post("/api/rooms?name=" + roomName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.name").value("Кабинет"));

        verify(roomService, times(1)).createRoom(roomName);
        verify(roomMapper, times(1)).toDto(savedRoom);
    }

    @Test
    void getRoomById_whenRoomExists_shouldReturnRoom() throws Exception {
        Long roomId = 1L;
        RoomEntity room = new RoomEntity(roomId, "Гостиная");
        RoomDto dto = new RoomDto(roomId, "Гостиная");

        when(roomService.getRoomById(roomId)).thenReturn(room);
        when(roomMapper.toDto(room)).thenReturn(dto);

        mockMvc.perform(get("/api/rooms/{id}", roomId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Гостиная"));

        verify(roomService, times(1)).getRoomById(roomId);
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