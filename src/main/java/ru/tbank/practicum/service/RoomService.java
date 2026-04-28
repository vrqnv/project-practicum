package ru.tbank.practicum.service;

import ru.tbank.practicum.entity.BatteryEntity;
import ru.tbank.practicum.entity.BlindsEntity;
import ru.tbank.practicum.entity.RoomEntity;
import ru.tbank.practicum.repository.BatteryRepository;
import ru.tbank.practicum.repository.BlindsRepository;
import ru.tbank.practicum.repository.RoomRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoomService {

    private static final Logger log = LoggerFactory.getLogger(RoomService.class);
    private final RoomRepository roomRepository;
    private final BatteryRepository batteryRepository;
    private final BlindsRepository blindsRepository;

    public RoomService(RoomRepository roomRepository,
                       BatteryRepository batteryRepository,
                       BlindsRepository blindsRepository) {
        this.roomRepository = roomRepository;
        this.batteryRepository = batteryRepository;
        this.blindsRepository = blindsRepository;
    }

    @Transactional
    public RoomEntity createRoom(String name) {
        RoomEntity room = new RoomEntity();
        room.setName(name);
        RoomEntity savedRoom = roomRepository.save(room);
        log.info("Создана комната: {} (id={})", name, savedRoom.getId());

        BatteryEntity battery = new BatteryEntity();
        battery.setRoomId(savedRoom.getId());
        battery.setTemperature(22);
        batteryRepository.save(battery);
        log.info("Создана батарея для комнаты {} с температурой 22C", savedRoom.getId());

        BlindsEntity blinds = new BlindsEntity();
        blinds.setRoomId(savedRoom.getId());
        blinds.setState("closed");
        blindsRepository.save(blinds);
        log.info("Созданы жалюзи для комнаты {} в положении closed", savedRoom.getId());

        return savedRoom;
    }

    @Transactional(readOnly = true)
    public List<RoomEntity> getAllRooms() {
        return roomRepository.findAll();
    }

    @Transactional(readOnly = true)
    public RoomEntity getRoomById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Комната с id=" + id + " не найдена"));
    }

    @Transactional
    public void deleteRoom(Long id) {
        getRoomById(id);
        roomRepository.deleteById(id);
        log.info("Комната {} удалена", id);
    }
}