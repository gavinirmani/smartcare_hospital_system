package com.smartcare.service.payment;

import com.smartcare.entity.PaymentMethod;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CardPaymentService implements PaymentService {

    @Override
    public PaymentMethod getPaymentMethod() {
        return PaymentMethod.Card;
    }

    @Override
    public boolean processPayment(String billId, BigDecimal amount) {
        // Custom terminal/card processing logic
        System.out.println("Processing card payment of $" + amount + " for Bill: " + billId);
        return true;
    }
}