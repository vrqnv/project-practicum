package ru.tbank.practicum.controller;

import ru.tbank.practicum.entity.RoomEntity;
import ru.tbank.practicum.service.RoomService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping
    public RoomEntity createRoom(@RequestParam String name) {
        return roomService.createRoom(name);
    }

    @GetMapping
    public List<RoomEntity> getAllRooms() {
        return roomService.getAllRooms();
    }

    @GetMapping("/{id}")
    public RoomEntity getRoomById(@PathVariable Long id) {
        return roomService.getRoomById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return "Комната с id=" + id + " удалена вместе со всеми устройствами";
    }
}