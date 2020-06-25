package com.adison.shop.payments;

import lombok.extern.java.Log;

import java.time.Instant;

@Log
public class FakePaymentService {

    private static final String logEntry = "A new payment for %s has been initiated";

    //breaks dependency inversion of principle, since we have concretions dependent one on another
    //above all, this breaks the single responsibility principle, since a payment service should process payments
    //and not generate ids
    private final UUIDPaymentIdGenerator uuidPaymentIdGenerator = new UUIDPaymentIdGenerator();


    public Payment process(PaymentRequest paymentRequest) {
        var payment =  Payment.builder()
                .id(uuidPaymentIdGenerator.getNext())
                .money(paymentRequest.getMoney())
                .timestamp(Instant.now())
                .status(PaymentStatus.STARTED)
                .build();

        log.info(createLogEntry(payment));
        return payment;
    }

    //log here for now, using Lombok's logger object
    private String createLogEntry(Payment payment) {
        return String.format(logEntry, payment.getMoney());
    }
}
