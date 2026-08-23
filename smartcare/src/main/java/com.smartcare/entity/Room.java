package com.smartcare.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@Entity
@Table(name = "room")
public class Room {

    @Id
    @Column(name = "room_id", length = 10)
    private String roomId;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private RoomCategory category;

    @Enumerated(EnumType.STRING)
    @Column(name = "room_availability", nullable = false)
    private RoomAvailability roomAvailability = RoomAvailability.Available;

    @Column(name = "charge_per_day", precision = 10, scale = 2)
    private BigDecimal chargePerDay = BigDecimal.ZERO;

    public Room() {
    }

    public Room(String roomId, RoomCategory category, RoomAvailability roomAvailability, BigDecimal chargePerDay) {
        this.roomId = roomId;
        this.category = category;
        this.roomAvailability = roomAvailability;
        this.chargePerDay = chargePerDay;
    }

}