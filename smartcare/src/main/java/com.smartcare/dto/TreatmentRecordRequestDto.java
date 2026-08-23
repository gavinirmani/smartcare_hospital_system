package com.smartcare.dto;

import java.time.LocalDate;

public record TreatmentRecordRequestDto(
        String treatmentId,
        String patientId,
        String doctorId,
        String diagnosis,
        String prescriptionDetails,
        String treatmentNotes,
        LocalDate treatmentDate
) {}