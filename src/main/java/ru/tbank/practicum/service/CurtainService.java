package ru.tbank.practicum.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CurtainService {

    private static final Logger log = LoggerFactory.getLogger(CurtainService.class);

    private String scheduleTime = null;
    private String scheduleAction = null;

    public void setSchedule(String time, String action) {
        this.scheduleTime = time;
        this.scheduleAction = action;
        log.info("Шторы: расписание установлено на {} - {}", time, action);
    }

    public String getScheduleTime() {
        return scheduleTime;
    }

    public String getScheduleAction() {
        return scheduleAction;
    }
}