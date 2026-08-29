package com.smartcare.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "room")
public class Room {

    @Id
    @Column(name = "room_id", length = 10)
    private String roomId;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "room_availability", nullable = false)
    private String roomAvailability;

    @Column(name = "charge_per_day", precision = 10, scale = 2)
    private BigDecimal chargePerDay;

    public Room() {
    }

    public Room(
            String roomId,
            RoomCategory category,
            RoomAvailability roomAvailability,
            BigDecimal chargePerDay
    ) {
        this.roomId = roomId;
        this.category = category != null
                ? category.getValue()
                : null;
        this.roomAvailability = roomAvailability != null
                ? roomAvailability.getValue()
                : null;
        this.chargePerDay = chargePerDay;
    }

    public RoomCategory getCategory() {
        if (category == null) {
            return null;
        }

        return RoomCategory.fromValue(category);
    }

    public void setCategory(RoomCategory category) {
        this.category = category != null
                ? category.getValue()
                : null;
    }

    public RoomAvailability getRoomAvailability() {
        if (roomAvailability == null) {
            return null;
        }

        return RoomAvailability.fromValue(roomAvailability);
    }

    public void setRoomAvailability(RoomAvailability roomAvailability) {
        this.roomAvailability = roomAvailability != null
                ? roomAvailability.getValue()
                : null;
    }
}