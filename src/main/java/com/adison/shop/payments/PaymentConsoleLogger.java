package com.adison.shop.payments;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;

import java.util.Locale;

//orders the aspects on a particular wrapped method.
//@Order(3)
@Aspect
@Log
@RequiredArgsConstructor
public class PaymentConsoleLogger implements Ordered {

    private static final String MESSAGE_KEY = "paymentInfo";

    private final MessageSource messageSource;
    private final ApplicationEventPublisher eventPublisher;

    //pointcut == named designator
    @Pointcut("@annotation(LogPayments)")
    public void logPayments() {
    }

    @Before(value = "logPayments() && args(paymentRequest)")
    public void beforePayment(PaymentRequest paymentRequest) {
        log.info("New payment request: " + paymentRequest);
    }

    //run independently of the outcome of the core method
    @After(value = "logPayments()")
    public void AfterPayment() {
        log.info("After payment");
    }

    @AfterThrowing(value = "logPayments()", throwing = "exception")
    public void onException(Exception exception) {
        log.info("Payment exception: " + exception.getClass().getSimpleName());
    }

    @AfterReturning(value = "logPayments()", returning = "payment")
    public void log(Payment payment) {
        log.info(createLogEntry(payment));
    }

    @AfterReturning(value = "logPayments()", returning = "payment")
    public void onPaymentStatusChange(Payment payment) {
        eventPublisher.publishEvent(new PaymentStatusChangedEvent(this, payment));
    }

    private String createLogEntry(Payment payment) {
        return messageSource.getMessage(MESSAGE_KEY, new String[]{payment.getMoney().toString()},
                Locale.getDefault());
    }

    @Override
    public int getOrder() {
        return 3;
    }
}

