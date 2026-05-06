package ru.tbank.practicum.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "blinds")
public class BlindsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room_id", nullable = false)
    private Long roomId;

    @Column(name = "state", nullable = false, length = 10)
    private String state;

    public BlindsEntity() {}

    public BlindsEntity(Long id, Long roomId, String state) {
        this.id = id;
        this.roomId = roomId;
        this.state = state;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}