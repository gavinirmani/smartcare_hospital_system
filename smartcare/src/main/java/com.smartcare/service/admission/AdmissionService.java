package com.smartcare.service.admission;

import com.smartcare.dto.AdmissionRequestDto;
import com.smartcare.dto.AdmissionResponseDto;
import com.smartcare.entity.Admission;

import java.util.List;

public interface AdmissionService {
    AdmissionResponseDto createAdmission(AdmissionRequestDto requestDto);
    AdmissionResponseDto getAdmissionById(String admissionId);
    List<AdmissionResponseDto> getAllAdmissions();
    List<AdmissionResponseDto> getAdmissionsByPatient(String patientId);
    AdmissionResponseDto dischargePatient(String admissionId);
    void deleteAdmission(String admissionId);

    Admission saveAdmission(Admission admission);
}