package com.smartcare.service.laboratory;

import com.smartcare.dto.LabTestRequestDto;
import com.smartcare.dto.LabTestResponseDto;

import java.util.List;

public interface LabTestService {

    // CREATE
    LabTestResponseDto createLabTest(
            LabTestRequestDto requestDto
    );

    // GET BY ID
    LabTestResponseDto getLabTestById(
            String labTestId
    );

    // GET ALL
    List<LabTestResponseDto> getAllLabTests();

    // GET BY PATIENT
    List<LabTestResponseDto> getLabTestsByPatient(
            String patientId
    );

    // GET BY DOCTOR
    List<LabTestResponseDto> getLabTestsByDoctor(
            String doctorId
    );

    // FULL UPDATE
    LabTestResponseDto updateLabTest(
            String labTestId,
            LabTestRequestDto requestDto
    );

    // UPDATE RESULT ONLY
    LabTestResponseDto updateLabTestResult(
            String labTestId,
            String result,
            String technicianName
    );

    // DELETE
    void deleteLabTest(
            String labTestId
    );
}