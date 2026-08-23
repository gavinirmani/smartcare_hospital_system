package com.smartcare.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@Entity
@Table(name = "bill")
public class Bill {

    @Id
    @Column(name = "bill_id", length = 10)
    private String billId;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "appointment_id", nullable = false)
    private Appointment appointment;

    @Column(name = "bill_date")
    private LocalDate billDate;

    @Column(name = "consultation_charges", precision = 10, scale = 2)
    private BigDecimal consultationCharges = BigDecimal.ZERO;

    @Column(name = "room_charges", precision = 10, scale = 2)
    private BigDecimal roomCharges = BigDecimal.ZERO;

    @Column(name = "lab_charges", precision = 10, scale = 2)
    private BigDecimal labCharges = BigDecimal.ZERO;

    @Column(name = "medicine_charges", precision = 10, scale = 2)
    private BigDecimal medicineCharges = BigDecimal.ZERO;

    @Column(name = "total_amount", precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    private PaymentStatus paymentStatus = PaymentStatus.Unpaid;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;

    public Bill() {
    }

    public Bill(String billId, Patient patient, Appointment appointment, LocalDate billDate, BigDecimal consultationCharges, BigDecimal roomCharges, BigDecimal labCharges, BigDecimal medicineCharges, BigDecimal totalAmount, PaymentStatus paymentStatus, PaymentMethod paymentMethod) {
        this.billId = billId;
        this.patient = patient;
        this.appointment = appointment;
        this.billDate = billDate;
        this.consultationCharges = consultationCharges;
        this.roomCharges = roomCharges;
        this.labCharges = labCharges;
        this.medicineCharges = medicineCharges;
        this.totalAmount = totalAmount;
        this.paymentStatus = paymentStatus;
        this.paymentMethod = paymentMethod;
    }

}