package com.smartcare.service.treatment;

import com.smartcare.dto.TreatmentRecordRequestDto;
import com.smartcare.dto.TreatmentRecordResponseDto;

import java.util.List;

public interface TreatmentRecordService {
    TreatmentRecordResponseDto createTreatmentRecord(TreatmentRecordRequestDto requestDto);
    TreatmentRecordResponseDto getTreatmentRecordById(String treatmentId);
    List<TreatmentRecordResponseDto> getAllTreatmentRecords();
    List<TreatmentRecordResponseDto> getTreatmentRecordsByPatient(String patientId);
    List<TreatmentRecordResponseDto> getTreatmentRecordsByDoctor(String doctorId);
    TreatmentRecordResponseDto updateTreatmentRecord(String treatmentId, TreatmentRecordRequestDto requestDto);
    void deleteTreatmentRecord(String treatmentId);
}