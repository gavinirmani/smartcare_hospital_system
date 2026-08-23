package com.smartcare.dto;

import com.smartcare.entity.RoomAvailability;
import com.smartcare.entity.RoomCategory;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class RoomResponseDto {

    private String roomId;
    private RoomCategory category;
    private RoomAvailability roomAvailability;
    private BigDecimal chargePerDay;

    public RoomResponseDto() {
    }

    public RoomResponseDto(String roomId, RoomCategory category, RoomAvailability roomAvailability, BigDecimal chargePerDay) {
        this.roomId = roomId;
        this.category = category;
        this.roomAvailability = roomAvailability;
        this.chargePerDay = chargePerDay;
    }

}