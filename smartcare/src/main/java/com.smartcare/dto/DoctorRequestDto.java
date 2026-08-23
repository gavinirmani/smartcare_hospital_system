package com.smartcare.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class DoctorRequestDto {

    @NotBlank(message = "Doctor ID is required")
    private String doctorId;

    @NotBlank(message = "Doctor name is required")
    private String doctorName;

    @NotBlank(message = "Specialization is required")
    private String specialization;

    @NotBlank(message = "Qualification is required")
    private String qualification;

    @NotBlank(message = "Contact number is required")
    @Pattern(
            regexp = "^[0-9]{10,15}$",
            message = "Contact number must contain 10 to 15 digits"
    )
    private String contactNumber;

    @NotNull(message = "Consultation fee is required")
    @DecimalMin(
            value = "0.01",
            message = "Consultation fee must be greater than 0"
    )
    private BigDecimal consultationFee;

    @NotBlank(message = "Department ID is required")
    private String departmentId;

}