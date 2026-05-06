package ru.tbank.practicum.controller;

import ru.tbank.practicum.service.BatteryService;
import ru.tbank.practicum.service.BlindsService;
import ru.tbank.practicum.service.CurtainService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {
    private final BatteryService batteryService;
    private final BlindsService blindsService;
    private final CurtainService curtainService;

    public DeviceController(
            BatteryService batteryService,
            BlindsService blindsService,
            CurtainService curtainService) {
        this.batteryService = batteryService;
        this.blindsService = blindsService;
        this.curtainService = curtainService;
    }

    @PutMapping("/battery/temperature")
    public String setBatteryTemperature(@RequestParam Long roomId, @RequestParam int value) {
        batteryService.setTemperature(roomId, value);
        return String.format("Температура батареи в комнате %d установлена на %d°C", roomId, value);
    }

    @PutMapping("/blinds")
    public String setBlindsState(@RequestParam Long roomId, @RequestParam String state) {
        blindsService.setState(roomId, state);
        return String.format("Жалюзи в комнате %d %s", roomId, state.equals("open") ? "открыты" : "закрыты");
    }

    @PostMapping("/curtains/schedule")
    public String setCurtainSchedule(
            @RequestParam Long roomId,
            @RequestParam String time,
            @RequestParam String action) {
        curtainService.setSchedule(roomId, time, action);
        return String.format("Расписание для комнаты %d установлено: в %s %s шторы", roomId, time, action);
    }

    @PostMapping("/battery")
    public ResponseEntity<String> createBattery(@RequestParam Long roomId) {
        try {
            batteryService.createBattery(roomId);
            return ResponseEntity.ok(String.format("Батарея создана для комнаты %d с температурой 22°C", roomId));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/blinds")
    public ResponseEntity<String> createBlinds(@RequestParam Long roomId) {
        try {
            blindsService.createBlinds(roomId);
            return ResponseEntity.ok(String.format("Жалюзи созданы для комнаты %d в положении closed", roomId));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}