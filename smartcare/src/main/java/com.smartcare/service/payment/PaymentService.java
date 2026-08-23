package com.smartcare.service.payment;

import com.smartcare.entity.PaymentMethod;
import java.math.BigDecimal;

public interface PaymentService {
    PaymentMethod getPaymentMethod();
    boolean processPayment(String billId, BigDecimal amount);
}