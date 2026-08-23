package com.smartcare.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@Entity
@Table(name = "lab_test")
public class LabTest {

    @Id
    @Column(name = "lab_test_id", length = 10)
    private String labTestId;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @Column(name = "test_name", nullable = false, length = 100)
    private String testName;

    @Column(name = "test_date", nullable = false)
    private LocalDate testDate;

    @Column(name = "test_result", length = 255)
    private String testResult;

    @Column(name = "technician_name", length = 100)
    private String technicianName;

    @Enumerated(EnumType.STRING)
    @Column(name = "lab_test_status", nullable = false)
    private LabTestStatus labTestStatus = LabTestStatus.Pending;

    @Column(name = "test_charge", precision = 10, scale = 2)
    private BigDecimal testCharge;

    public LabTest() {
    }

    public LabTest(String labTestId, Patient patient, Doctor doctor, String testName, LocalDate testDate, String testResult, String technicianName, LabTestStatus labTestStatus, BigDecimal testCharge) {
        this.labTestId = labTestId;
        this.patient = patient;
        this.doctor = doctor;
        this.testName = testName;
        this.testDate = testDate;
        this.testResult = testResult;
        this.technicianName = technicianName;
        this.labTestStatus = labTestStatus;
        this.testCharge = testCharge;
    }

}