package com.smartcare.dto;

import com.smartcare.entity.LabTestStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public record LabTestRequestDto(
        String labTestId,
        String patientId,
        String doctorId,
        String testName,
        LocalDate testDate,
        String testResult,
        String technicianName,
        LabTestStatus labTestStatus,
        BigDecimal testCharge
) {

}