package ru.tbank.practicum.util;

import ru.tbank.practicum.entity.BatteryEntity;
import ru.tbank.practicum.entity.BlindsEntity;
import ru.tbank.practicum.entity.CurtainScheduleEntity;
import ru.tbank.practicum.entity.RoomEntity;

import java.time.LocalTime;
import java.util.List;
import java.util.Random;

import static ru.tbank.practicum.util.AppConstants.DEFAULT_BLINDS_STATE;
import static ru.tbank.practicum.util.AppConstants.DEFAULT_TEMPERATURE;

public class TestData {

    private static final Random random = new Random();
    private static final List<String> ROOM_NAMES = List.of("Гостиная", "Спальня", "Кухня", "Ванная", "Кабинет");

    public static RoomEntity randomRoom() {
        RoomEntity room = new RoomEntity();
        room.setId(random.nextLong(1, Long.MAX_VALUE));
        room.setName(ROOM_NAMES.get(random.nextInt(ROOM_NAMES.size())));
        return room;
    }

    public static BatteryEntity batteryWithRoomId(Long roomId) {
        BatteryEntity battery = new BatteryEntity();
        battery.setRoomId(roomId);
        battery.setTemperature(DEFAULT_TEMPERATURE);
        return battery;
    }

    public static BlindsEntity blindsWithRoomId(Long roomId) {
        BlindsEntity blinds = new BlindsEntity();
        blinds.setRoomId(roomId);
        blinds.setState(DEFAULT_BLINDS_STATE);
        return blinds;
    }

    public static CurtainScheduleEntity curtainScheduleWithRoomId(Long roomId, String time, String action) {
        CurtainScheduleEntity schedule = new CurtainScheduleEntity();
        schedule.setRoomId(roomId);
        schedule.setScheduleTime(LocalTime.parse(time));
        schedule.setScheduleAction(action);
        return schedule;
    }
}