package com.smartcare.service.payment;

import com.smartcare.entity.PaymentMethod;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class PaymentFactory {

    private final Map<PaymentMethod, PaymentService> paymentServices;


    public PaymentFactory(List<PaymentService> services) {
        this.paymentServices = services.stream()
                .collect(Collectors.toMap(PaymentService::getPaymentMethod, Function.identity()));
    }

    public PaymentService getService(PaymentMethod method) {
        PaymentService service = paymentServices.get(method);
        if (service == null) {
            throw new IllegalArgumentException("Unsupported payment method: " + method);
        }
        return service;
    }
}