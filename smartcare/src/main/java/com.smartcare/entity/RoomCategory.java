package com.smartcare.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum RoomCategory {

    General_Ward,
    Private_Room,
    ICU;

    @JsonValue
    public String getValue() {
        return name().replace("_", " ");
    }

    @JsonCreator
    public static RoomCategory fromValue(String value) {

        if (value == null) {
            return null;
        }

        for (RoomCategory category : values()) {
            if (category.getValue().equalsIgnoreCase(value)
                    || category.name().equalsIgnoreCase(value)) {
                return category;
            }
        }

        throw new IllegalArgumentException(
                "Unknown room category: " + value
        );
    }
}