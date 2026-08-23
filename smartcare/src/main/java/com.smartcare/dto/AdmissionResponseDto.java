package com.smartcare.dto;

import com.smartcare.entity.AdmissionStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Optional;

@Setter
@Getter
public class AdmissionResponseDto {

    private String admissionId;
    private String patientId;
    private String patientName;
    private String roomId;
    private LocalDate admissionDate;
    private String bedNo;
    private AdmissionStatus admissionStatus;
    private LocalDate dischargeDate;

    public AdmissionResponseDto() {
    }

    public AdmissionResponseDto(String admissionId, String patientId, String patientName, String roomId, LocalDate admissionDate, String bedNo, AdmissionStatus admissionStatus, LocalDate dischargeDate) {
        this.admissionId = admissionId;
        this.patientId = patientId;
        this.patientName = patientName;
        this.roomId = roomId;
        this.admissionDate = admissionDate;
        this.bedNo = bedNo;
        this.admissionStatus = admissionStatus;
        this.dischargeDate = dischargeDate;
    }

    public Optional<ResponseEntity<Object>> map(Object o) {
        return Optional.empty();
    }
}