package ru.tbank.practicum.controller;

import ru.tbank.practicum.dto.RoomDto;
import ru.tbank.practicum.entity.RoomEntity;
import ru.tbank.practicum.mapper.RoomMapper;
import ru.tbank.practicum.service.RoomService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;
    private final RoomMapper roomMapper;

    public RoomController(RoomService roomService, RoomMapper roomMapper) {
        this.roomService = roomService;
        this.roomMapper = roomMapper;
    }

    @PostMapping
    public RoomDto createRoom(@RequestParam String name) {
        RoomEntity room = roomService.createRoom(name);
        return roomMapper.toDto(room);
    }

    @GetMapping
    public List<RoomDto> getAllRooms() {
        return roomService.getAllRooms().stream()
                .map(roomMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public RoomDto getRoomById(@PathVariable Long id) {
        RoomEntity room = roomService.getRoomById(id);
        return roomMapper.toDto(room);
    }

    @DeleteMapping("/{id}")
    public String deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return "Комната с id=" + id + " удалена вместе со всеми устройствами";
    }
}