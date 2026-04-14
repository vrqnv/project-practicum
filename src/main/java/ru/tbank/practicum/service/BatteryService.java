package ru.tbank.practicum.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BatteryService {
    private static final Logger log = LoggerFactory.getLogger(BatteryService.class);
    private int temperature = 22;

    public void setTemperature(int value) {
        this.temperature = value;
        log.info("Температура батареи изменена на {}", temperature);
    }

    public int getTemperature() {
        return temperature;
    }
}