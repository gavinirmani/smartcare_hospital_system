package com.smartcare.dto;

import com.smartcare.entity.PaymentMethod; import com.smartcare.entity.PaymentStatus; import java.math.BigDecimal;

public record PaymentResultDto(String billId, BigDecimal amount, PaymentMethod paymentMethod, PaymentStatus paymentStatus, String message) {}
