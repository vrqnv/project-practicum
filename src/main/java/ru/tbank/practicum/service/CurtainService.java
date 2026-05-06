package ru.tbank.practicum.service;

import ru.tbank.practicum.entity.CurtainScheduleEntity;
import ru.tbank.practicum.repository.CurtainScheduleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;

@Service
public class CurtainService {

    private static final Logger log = LoggerFactory.getLogger(CurtainService.class);
    private final CurtainScheduleRepository curtainScheduleRepository;

    public CurtainService(CurtainScheduleRepository curtainScheduleRepository) {
        this.curtainScheduleRepository = curtainScheduleRepository;
    }

    @Transactional
    public void setSchedule(Long roomId, String time, String action) {
        CurtainScheduleEntity schedule = curtainScheduleRepository.findByRoomId(roomId)
                .orElse(new CurtainScheduleEntity());

        schedule.setRoomId(roomId);
        schedule.setScheduleTime(LocalTime.parse(time));
        schedule.setScheduleAction(action);

        curtainScheduleRepository.save(schedule);
        log.info("Шторы в комнате {}: расписание установлено на {} - {}", roomId, time, action);
    }

    @Transactional(readOnly = true)
    public String getScheduleTime(Long roomId) {
        return curtainScheduleRepository.findByRoomId(roomId)
                .map(s -> s.getScheduleTime().toString())
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public String getScheduleAction(Long roomId) {
        return curtainScheduleRepository.findByRoomId(roomId)
                .map(CurtainScheduleEntity::getScheduleAction)
                .orElse(null);
    }
}