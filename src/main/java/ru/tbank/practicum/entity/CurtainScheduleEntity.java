package ru.tbank.practicum.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "curtain_schedule")
public class CurtainScheduleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room_id", nullable = false)
    private Long roomId;

    @Column(name = "schedule_time", nullable = false, length = 10)
    private String scheduleTime;

    @Column(name = "schedule_action", nullable = false, length = 10)
    private String scheduleAction;

    public CurtainScheduleEntity() {}

    public CurtainScheduleEntity(Long id, Long roomId, String scheduleTime, String scheduleAction) {
        this.id = id;
        this.roomId = roomId;
        this.scheduleTime = scheduleTime;
        this.scheduleAction = scheduleAction;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getScheduleTime() {
        return scheduleTime;
    }

    public void setScheduleTime(String scheduleTime) {
        this.scheduleTime = scheduleTime;
    }

    public String getScheduleAction() {
        return scheduleAction;
    }

    public void setScheduleAction(String scheduleAction) {
        this.scheduleAction = scheduleAction;
    }
}