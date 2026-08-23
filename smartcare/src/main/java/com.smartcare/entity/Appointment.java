package com.smartcare.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
@Entity
@Table(
        name = "appointment",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_doctor_schedule",
                        columnNames = {"doctor_id", "appointment_date", "appointment_time"}
                )
        }
)
public class Appointment {

    @Id
    @Column(name = "appointment_id", length = 10)
    private String appointmentId;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @Column(name = "appointment_date", nullable = false)
    private LocalDate appointmentDate;

    @Column(name = "appointment_time", nullable = false)
    private LocalTime appointmentTime;

    @Column(name = "consultation_room", length = 20, nullable = false)
    private String consultationRoom;

    @Enumerated(EnumType.STRING)
    @Column(name = "appointment_status", nullable = false)
    private AppointmentStatus appointmentStatus = AppointmentStatus.Scheduled;

    public Appointment() {
    }

    public Appointment(String appointmentId, Patient patient, Doctor doctor, LocalDate appointmentDate, LocalTime appointmentTime, String consultationRoom, AppointmentStatus appointmentStatus) {
        this.appointmentId = appointmentId;
        this.patient = patient;
        this.doctor = doctor;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.consultationRoom = consultationRoom;
        this.appointmentStatus = appointmentStatus;
    }

}