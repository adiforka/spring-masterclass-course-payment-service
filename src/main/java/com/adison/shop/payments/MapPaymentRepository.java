package com.adison.shop.payments;

import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

public class MapPaymentRepository implements PaymentRepository {

    @Setter
    private Map<String, Payment> payments = new HashMap<>();

    @Override
    public Payment save(Payment payment) {
        payments.put(payment.getId(), payment);
        return payment;
    }
}