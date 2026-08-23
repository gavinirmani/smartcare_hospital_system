package com.smartcare.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "admission")
public class Admission {

    @Id
    @Column(name = "admission_id", length = 10)
    private String admissionId;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @Column(name = "admission_date", nullable = false)
    private LocalDate admissionDate;

    @Column(name = "bed_no", length = 10, nullable = false)
    private String bedNo;

    @Enumerated(EnumType.STRING)
    @Column(name = "admission_status", nullable = false)
    private AdmissionStatus admissionStatus = AdmissionStatus.Admitted;

    @Column(name = "discharge_date")
    private LocalDate dischargeDate;

    public Admission() {
    }

    public Admission(String admissionId, Patient patient, Room room,
                     LocalDate admissionDate, String bedNo,
                     AdmissionStatus admissionStatus,
                     LocalDate dischargeDate) {
        this.admissionId = admissionId;
        this.patient = patient;
        this.room = room;
        this.admissionDate = admissionDate;
        this.bedNo = bedNo;
        this.admissionStatus = admissionStatus;
        this.dischargeDate = dischargeDate;
    }
}