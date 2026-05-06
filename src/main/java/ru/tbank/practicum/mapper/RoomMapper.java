package ru.tbank.practicum.mapper;

import ru.tbank.practicum.dto.RoomDto;
import ru.tbank.practicum.entity.RoomEntity;
import org.springframework.stereotype.Component;

@Component
public class RoomMapper {

    public RoomDto toDto(RoomEntity entity) {
        if (entity == null) {
            return null;
        }
        return new RoomDto(entity.getId(), entity.getName());
    }
}