package com.smartcare.dto;

import com.smartcare.entity.AdmissionStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class AdmissionRequestDto {

    private String admissionId;
    private String patientId;
    private String roomId;
    private LocalDate admissionDate;
    private String bedNo;
    private AdmissionStatus admissionStatus;
    private LocalDate dischargeDate;

    public AdmissionRequestDto() {
    }

    public AdmissionRequestDto(String admissionId, String patientId, String roomId, LocalDate admissionDate, String bedNo, AdmissionStatus admissionStatus, LocalDate dischargeDate) {
        this.admissionId = admissionId;
        this.patientId = patientId;
        this.roomId = roomId;
        this.admissionDate = admissionDate;
        this.bedNo = bedNo;
        this.admissionStatus = admissionStatus;
        this.dischargeDate = dischargeDate;
    }

}