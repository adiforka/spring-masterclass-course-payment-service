package com.adison.shop.payments;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
//will provide constructor with final fields as parameters (or all not null-annotated fields, if you have any)
public class FakePaymentService implements PaymentService {

    //used an interface here, even though course uses FakePaymentService
    private final PaymentIdGenerator paymentIdGenerator;

    @LogPayments
    @Override
    public Payment process(PaymentRequest paymentRequest) {
        return Payment.builder()
                .id(paymentIdGenerator.getNext())
                .money(paymentRequest.getMoney())
                .timestamp(Instant.now())
                .status(PaymentStatus.STARTED)
                .build();
    }
}
