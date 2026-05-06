package ru.tbank.practicum.dto;

public class BatteryDto {
    private Long roomId;
    private int temperature;

    public BatteryDto(Long roomId, int temperature) {
        this.roomId = roomId;
        this.temperature = temperature;
    }

    public Long getRoomId() {
        return roomId;
    }

    public int getTemperature() {
        return temperature;
    }
}