package com.smartcare.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum RoomCategory {
    GENERAL_WARD("General Ward"),
    PRIVATE_ROOM("Private Room"),
    ICU("ICU");

    private final String value;

    RoomCategory(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}