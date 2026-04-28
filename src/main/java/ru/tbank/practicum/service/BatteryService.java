package ru.tbank.practicum.service;

import ru.tbank.practicum.entity.BatteryEntity;
import ru.tbank.practicum.repository.BatteryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BatteryService {

    private static final Logger log = LoggerFactory.getLogger(BatteryService.class);
    private final BatteryRepository batteryRepository;

    public BatteryService(BatteryRepository batteryRepository) {
        this.batteryRepository = batteryRepository;
    }

    @Transactional
    public void setTemperature(Long roomId, int value) {
        BatteryEntity battery = batteryRepository.findByRoomId(roomId)
                .orElse(new BatteryEntity());
        battery.setRoomId(roomId);
        battery.setTemperature(value);
        batteryRepository.save(battery);
        log.info("Батарея в комнате {}: температура изменена на {}°C", roomId, value);
    }

    @Transactional(readOnly = true)
    public int getTemperature(Long roomId) {
        return batteryRepository.findByRoomId(roomId)
                .map(BatteryEntity::getTemperature)
                .orElse(22);
    }
}