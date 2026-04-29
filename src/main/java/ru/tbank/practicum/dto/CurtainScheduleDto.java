package ru.tbank.practicum.dto;

public class CurtainScheduleDto {
    private Long roomId;
    private String scheduleTime;
    private String scheduleAction;

    public CurtainScheduleDto(Long roomId, String scheduleTime, String scheduleAction) {
        this.roomId = roomId;
        this.scheduleTime = scheduleTime;
        this.scheduleAction = scheduleAction;
    }

    public Long getRoomId() {
        return roomId;
    }

    public String getScheduleTime() {
        return scheduleTime;
    }

    public String getScheduleAction() {
        return scheduleAction;
    }
}