package ru.tbank.practicum.controller;

import org.springframework.http.ResponseEntity;
import ru.tbank.practicum.dto.RoomDevicesDto;
import ru.tbank.practicum.dto.RoomDto;
import ru.tbank.practicum.entity.RoomEntity;
import ru.tbank.practicum.mapper.RoomMapper;
import ru.tbank.practicum.service.BatteryService;
import ru.tbank.practicum.service.BlindsService;
import ru.tbank.practicum.service.CurtainService;
import ru.tbank.practicum.service.RoomService;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;
    private final RoomMapper roomMapper;
    private final BatteryService batteryService;
    private final BlindsService blindsService;
    private final CurtainService curtainService;

    public RoomController(RoomService roomService,
                          RoomMapper roomMapper,
                          BatteryService batteryService,
                          BlindsService blindsService,
                          CurtainService curtainService) {
        this.roomService = roomService;
        this.roomMapper = roomMapper;
        this.batteryService = batteryService;
        this.blindsService = blindsService;
        this.curtainService = curtainService;
    }

    private static final Logger log = LoggerFactory.getLogger(RoomController.class);

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

    @GetMapping("/{roomId}/devices")
    public ResponseEntity<?> getRoomDevices(@PathVariable Long roomId) {
        try {
            log.info("Запрос устройств для комнаты: {}", roomId);

            Integer temperature = batteryService.getTemperature(roomId);
            String blindsState = blindsService.getState(roomId);
            String scheduleTime = curtainService.getScheduleTime(roomId);
            String scheduleAction = curtainService.getScheduleAction(roomId);

            RoomDevicesDto dto = new RoomDevicesDto(roomId, temperature, blindsState, scheduleTime, scheduleAction);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            log.error("Ошибка при получении устройств комнаты {}: {}", roomId, e.getMessage(), e);
            return ResponseEntity.status(500).body("Ошибка: " + e.getMessage());
        }
    }
}