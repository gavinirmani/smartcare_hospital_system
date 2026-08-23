package com.smartcare.dto;

import com.smartcare.entity.AppointmentStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
public class AppointmentRequestDto {

    private String appointmentId;
    private String patientId;
    private String doctorId;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private String consultationRoom;
    private AppointmentStatus appointmentStatus;

    public AppointmentRequestDto() {
    }

    public AppointmentRequestDto(String appointmentId, String patientId, String doctorId, LocalDate appointmentDate, LocalTime appointmentTime, String consultationRoom, AppointmentStatus appointmentStatus) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.consultationRoom = consultationRoom;
        this.appointmentStatus = appointmentStatus;
    }

}