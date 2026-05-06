package ru.tbank.practicum.service;

import static ru.tbank.practicum.util.AppConstants.DEFAULT_TEMPERATURE;
import ru.tbank.practicum.entity.BatteryEntity;
import ru.tbank.practicum.kafka.EventProducer;
import ru.tbank.practicum.repository.BatteryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BatteryService {

    private static final Logger log = LoggerFactory.getLogger(BatteryService.class);
    private final BatteryRepository batteryRepository;
    private final EventProducer eventProducer;

    public BatteryService(BatteryRepository batteryRepository,
                          EventProducer eventProducer) {
        this.batteryRepository = batteryRepository;
        this.eventProducer = eventProducer;
    }

    @Transactional
    public void createBattery(Long roomId) {
        if (batteryRepository.findByRoomId(roomId).isPresent()) {
            throw new RuntimeException("Батарея уже существует в комнате " + roomId);
        }
        BatteryEntity battery = new BatteryEntity();
        battery.setRoomId(roomId);
        battery.setTemperature(DEFAULT_TEMPERATURE);
        batteryRepository.save(battery);
        log.info("Создана батарея для комнаты {} с температурой {}°C", roomId, DEFAULT_TEMPERATURE);
    }

    @Transactional
    public void setTemperature(Long roomId, int value) {
        BatteryEntity battery = batteryRepository.findByRoomId(roomId)
                .orElseThrow(() -> new RuntimeException("Батарея не найдена в комнате " + roomId));
        battery.setTemperature(value);
        batteryRepository.save(battery);
        log.info("Батарея в комнате {}: температура изменена на {}°C", roomId, value);

        String event = String.format("{\"eventType\":\"TEMPERATURE_CHANGED\",\"roomId\":%d,\"temperature\":%d}",
                roomId, value);
        eventProducer.send("temperature-events", event);
    }

    @Transactional(readOnly = true)
    public int getTemperature(Long roomId) {
        return batteryRepository.findByRoomId(roomId)
                .map(BatteryEntity::getTemperature)
                .orElse(DEFAULT_TEMPERATURE);
    }
}