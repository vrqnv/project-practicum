package ru.tbank.practicum.controller;

import ru.tbank.practicum.service.BatteryService;
import ru.tbank.practicum.service.BlindsService;
import ru.tbank.practicum.service.CurtainService;
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
        return "Температура батареи в комнате " + roomId + " установлена на " + value + "°C";
    }

    @PutMapping("/blinds")
    public String setBlindsState(@RequestParam Long roomId, @RequestParam String state) {
        blindsService.setState(roomId, state);
        return "Жалюзи в комнате " + roomId + " " + (state.equals("open") ? "открыты" : "закрыты");
    }

    @PostMapping("/curtains/schedule")
    public String setCurtainSchedule(
            @RequestParam Long roomId,
            @RequestParam String time,
            @RequestParam String action) {
        curtainService.setSchedule(roomId, time, action);
        return "Расписание для комнаты " + roomId + " установлено: в " + time + " " + action + " шторы";
    }
}