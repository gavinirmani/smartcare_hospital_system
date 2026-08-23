package com.smartcare.dto;

import com.smartcare.entity.LabTestStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public record LabTestResponseDto(
        String labTestId,
        String patientId,
        String patientName,
        String doctorId,
        String doctorName,
        String testName,
        LocalDate testDate,
        String testResult,
        String technicianName,
        LabTestStatus labTestStatus,
        BigDecimal testCharge
) {}