package ru.tbank.practicum.dto;

public class BlindsDto {
    private Long roomId;
    private String state;

    public BlindsDto(Long roomId, String state) {
        this.roomId = roomId;
        this.state = state;
    }

    public Long getRoomId() {
        return roomId;
    }

    public String getState() {
        return state;
    }
}