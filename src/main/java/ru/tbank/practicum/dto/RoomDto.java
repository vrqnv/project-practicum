package ru.tbank.practicum.dto;

public class RoomDto {
    private Long id;
    private String name;

    public RoomDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}