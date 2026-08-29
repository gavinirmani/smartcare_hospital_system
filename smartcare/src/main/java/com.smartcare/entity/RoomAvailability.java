package com.smartcare.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum RoomAvailability {

    AVAILABLE("Available"),
    OCCUPIED("Occupied");

    private final String value;

    RoomAvailability(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static RoomAvailability fromValue(String value) {
        for (RoomAvailability availability : RoomAvailability.values()) {
            if (availability.value.equalsIgnoreCase(value)) {
                return availability;
            }
        }

        throw new IllegalArgumentException(
                "Invalid room availability: " + value
        );
    }
}