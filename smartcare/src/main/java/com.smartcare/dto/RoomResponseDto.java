package com.smartcare.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RoomResponseDto {

    private String roomId;
    private String category;
    private String roomAvailability;
    private BigDecimal chargePerDay;

    public RoomResponseDto() {
    }

    public RoomResponseDto(
            String roomId,
            String category,
            String roomAvailability,
            BigDecimal chargePerDay
    ) {
        this.roomId = roomId;
        this.category = category;
        this.roomAvailability = roomAvailability;
        this.chargePerDay = chargePerDay;
    }
}