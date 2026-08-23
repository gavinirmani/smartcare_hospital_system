package com.smartcare.dto;

import com.smartcare.entity.PaymentMethod;
import com.smartcare.entity.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public record BillResponseDto(
        String billId,
        String patientId,
        String patientName,
        String appointmentId,
        LocalDate billDate,
        BigDecimal consultationCharges,
        BigDecimal roomCharges,
        BigDecimal labCharges,
        BigDecimal medicineCharges,
        BigDecimal totalAmount,
        PaymentStatus paymentStatus,
        PaymentMethod paymentMethod
) {}