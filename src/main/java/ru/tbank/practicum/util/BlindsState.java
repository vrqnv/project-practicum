package ru.tbank.practicum.util;

public enum BlindsState {
    OPEN("open"),
    CLOSED("closed");

    private final String value;

    BlindsState(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}