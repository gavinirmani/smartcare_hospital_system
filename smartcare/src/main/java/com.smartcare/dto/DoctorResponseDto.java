package com.smartcare.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class DoctorResponseDto {

    private String doctorId;
    private String doctorName;
    private String specialization;
    private String qualification;
    private String contactNumber;
    private BigDecimal consultationFee;
    private String departmentId;

    public DoctorResponseDto() {
    }

    public DoctorResponseDto(
            String doctorId,
            String doctorName,
            String specialization,
            String qualification,
            String contactNumber,
            BigDecimal consultationFee,
            String departmentId) {

        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.specialization = specialization;
        this.qualification = qualification;
        this.contactNumber = contactNumber;
        this.consultationFee = consultationFee;
        this.departmentId = departmentId;
    }

}