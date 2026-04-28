package ru.tbank.practicum.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "battery")
public class BatteryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room_id", nullable = false)
    private Long roomId;

    @Column(name = "temperature", nullable = false)
    private Integer temperature;

    // Конструктор по умолчанию (нужен JPA)
    public BatteryEntity() {}

    // Конструктор со всеми полями
    public BatteryEntity(Long id, Long roomId, Integer temperature) {
        this.id = id;
        this.roomId = roomId;
        this.temperature = temperature;
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

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }
}