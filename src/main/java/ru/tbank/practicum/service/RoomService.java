package ru.tbank.practicum.service;

import ru.tbank.practicum.entity.RoomEntity;
import ru.tbank.practicum.kafka.EventProducer;
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
    private final EventProducer eventProducer;

    public RoomService(RoomRepository roomRepository,
                       EventProducer eventProducer) {
        this.roomRepository = roomRepository;
        this.eventProducer = eventProducer;
    }

    @Transactional
    public RoomEntity createRoom(String name) {
        RoomEntity room = new RoomEntity();
        room.setName(name);
        RoomEntity savedRoom = roomRepository.save(room);
        log.info("Создана комната: {} (id={})", name, savedRoom.getId());

        String event = String.format("{\"eventType\":\"ROOM_CREATED\",\"roomId\":%d,\"roomName\":\"%s\"}",
                savedRoom.getId(), savedRoom.getName());
        eventProducer.send("room-events", event);

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