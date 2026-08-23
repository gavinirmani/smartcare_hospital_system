package com.smartcare.dto;

import java.time.LocalDate;

public record TreatmentRecordResponseDto(
        String treatmentId,
        String patientId,
        String patientName,
        String doctorId,
        String doctorName,
        String diagnosis,
        String prescriptionDetails,
        String treatmentNotes,
        LocalDate treatmentDate
) {}
