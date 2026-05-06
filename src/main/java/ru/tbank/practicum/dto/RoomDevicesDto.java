package ru.tbank.practicum.dto;

public class RoomDevicesDto {
    private Long roomId;
    private Integer temperature;
    private String blindsState;
    private String curtainScheduleTime;
    private String curtainScheduleAction;

    public RoomDevicesDto() {}

    public RoomDevicesDto(Long roomId, Integer temperature, String blindsState, String curtainScheduleTime, String curtainScheduleAction) {
        this.roomId = roomId;
        this.temperature = temperature;
        this.blindsState = blindsState;
        this.curtainScheduleTime = curtainScheduleTime;
        this.curtainScheduleAction = curtainScheduleAction;
    }

    public Long getRoomId() { return roomId; }
    public void setRoomId(Long roomId) { this.roomId = roomId; }
    public Integer getTemperature() { return temperature; }
    public void setTemperature(Integer temperature) { this.temperature = temperature; }
    public String getBlindsState() { return blindsState; }
    public void setBlindsState(String blindsState) { this.blindsState = blindsState; }
    public String getCurtainScheduleTime() { return curtainScheduleTime; }
    public void setCurtainScheduleTime(String curtainScheduleTime) { this.curtainScheduleTime = curtainScheduleTime; }
    public String getCurtainScheduleAction() { return curtainScheduleAction; }
    public void setCurtainScheduleAction(String curtainScheduleAction) { this.curtainScheduleAction = curtainScheduleAction; }
}