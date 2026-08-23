package com.smartcare.service.payment;

import com.smartcare.entity.PaymentMethod;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OnlinePaymentService implements PaymentService {

    @Override
    public PaymentMethod getPaymentMethod() {
        return PaymentMethod.Online;
    }

    @Override
    public boolean processPayment(String billId, BigDecimal amount) {
        // Custom payment gateway logic (e.g., Stripe, PayPal integration)
        System.out.println("Processing online payment gateway of $" + amount + " for Bill: " + billId);
        return true;
    }
}