package com.smartcare.service.payment;

import com.smartcare.entity.PaymentMethod;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CashPaymentService implements PaymentService {

    @Override
    public PaymentMethod getPaymentMethod() {
        return PaymentMethod.Cash;
    }

    @Override
    public boolean processPayment(String billId, BigDecimal amount) {
        // Custom cash handling logic (e.g., register log, receipt generation)
        System.out.println("Processing cash payment of $" + amount + " for Bill: " + billId);
        return true;
    }
}