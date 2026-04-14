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
    public String setBatteryTemperature(@RequestParam int value) {
        batteryService.setTemperature(value);
        return "Температура батареи установлена на " + value + "°C";
    }

    @PutMapping("/blinds")
    public String setBlindsState(@RequestParam String state) {
        blindsService.setState(state);
        return "Жалюзи " + (state.equals("open") ? "открыты" : "закрыты");
    }

    @PostMapping("/curtains/schedule")
    public String setCurtainSchedule(
            @RequestParam String time,
            @RequestParam String action) {
        curtainService.setSchedule(time, action);
        return "Расписание установлено: в " + time + " " + action + " шторы";
    }
}