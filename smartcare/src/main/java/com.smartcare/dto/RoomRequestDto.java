package com.smartcare.dto;

import com.smartcare.entity.RoomAvailability;
import com.smartcare.entity.RoomCategory;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RoomRequestDto {

    @NotBlank(message = "Room ID is required")
    private String roomId;

    @NotNull(message = "Room category is required")
    private RoomCategory category;

    @NotNull(message = "Room availability is required")
    private RoomAvailability roomAvailability;

    @NotNull(message = "Charge per day is required")
    @DecimalMin(
            value = "0.00",
            inclusive = true,
            message = "Charge per day cannot be negative"
    )
    private BigDecimal chargePerDay;
}