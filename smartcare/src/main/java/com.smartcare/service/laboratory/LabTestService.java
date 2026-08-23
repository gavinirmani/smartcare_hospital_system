package com.smartcare.service.laboratory;

import com.smartcare.dto.LabTestRequestDto;
import com.smartcare.dto.LabTestResponseDto;

import java.util.List;

public interface LabTestService {
    LabTestResponseDto createLabTest(LabTestRequestDto requestDto);
    LabTestResponseDto getLabTestById(String labTestId);
    List<LabTestResponseDto> getAllLabTests();
    List<LabTestResponseDto> getLabTestsByPatient(String patientId);
    List<LabTestResponseDto> getLabTestsByDoctor(String doctorId);
    LabTestResponseDto updateLabTestResult(String labTestId, String result, String technicianName);
    void deleteLabTest(String labTestId);
}