package ru.tbank.practicum.util;

public final class AppConstants {

    private AppConstants() {
    }

    public static final int DEFAULT_TEMPERATURE = 22;
    public static final String DEFAULT_BLINDS_STATE = "closed";

    public static final String CURTAIN_ACTION_OPEN = "open";
    public static final String CURTAIN_ACTION_CLOSE = "close";

    public static final String TOPIC_ROOM_EVENTS = "room-events";
    public static final String TOPIC_TEMPERATURE_EVENTS = "temperature-events";

    public static final String EVENT_ROOM_CREATED = "ROOM_CREATED";
    public static final String EVENT_TEMPERATURE_CHANGED = "TEMPERATURE_CHANGED";
}