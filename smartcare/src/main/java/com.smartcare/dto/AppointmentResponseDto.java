package com.smartcare.dto;

import com.smartcare.entity.AppointmentStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
public class AppointmentResponseDto {

    private String appointmentId;
    private String patientId;
    private String patientName;
    private String doctorId;
    private String doctorName;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private String consultationRoom;
    private AppointmentStatus appointmentStatus;

    public AppointmentResponseDto() {
    }

    public AppointmentResponseDto(String appointmentId, String patientId, String patientName, String doctorId, String doctorName, LocalDate appointmentDate, LocalTime appointmentTime, String consultationRoom, AppointmentStatus appointmentStatus) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.patientName = patientName;
        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.consultationRoom = consultationRoom;
        this.appointmentStatus = appointmentStatus;
    }

}