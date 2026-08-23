package com.smartcare.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientResponseDto {

    private String patientId;
    private String fullName;
    private LocalDate dob;
    private String gender;
    private String address;
    private String contactNumber;
    private String bloodGroup;
    private String emergencyContact;
}