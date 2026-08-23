package com.smartcare.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Entity
@Table(name = "treatment_record")
public class TreatmentRecord {
    @Id
    @Column(name = "treatment_id", length = 10)
    private String treatmentId;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @Column(name = "diagnosis", nullable = false, length = 255)
    private String diagnosis;

    @Column(name = "prescription_details", length = 255)
    private String prescriptionDetails;

    @Column(name = "treatment_notes", length = 255)
    private String treatmentNotes;

    @Column(name = "treatment_date", nullable = false)
    private LocalDate treatmentDate;

    public TreatmentRecord() {
    }

    public TreatmentRecord(String treatmentId, Patient patient, Doctor doctor, String diagnosis, String prescriptionDetails, String treatmentNotes, LocalDate treatmentDate) {
        this.treatmentId = treatmentId;
        this.patient = patient;
        this.doctor = doctor;
        this.diagnosis = diagnosis;
        this.prescriptionDetails = prescriptionDetails;
        this.treatmentNotes = treatmentNotes;
        this.treatmentDate = treatmentDate;
    }

}

