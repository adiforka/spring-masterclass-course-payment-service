package com.adison.shop;

import com.adison.shop.payments.*;
import lombok.extern.java.Log;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Log
public class Application {
    //tell the container where to look for configuration for the app
    private static final String BASE_PACKAGE = "com.adison.shop";

    public static void main(String[] args) {
        //annotation-based container. make sure to use TWR on this
        //to have it closed once the application exits
        try (AnnotationConfigApplicationContext applicationContext =
                     new AnnotationConfigApplicationContext(BASE_PACKAGE)) {

            //having configured AspectJ (including in the config class),
            // we can now ask Spring for an instance of PaymentService:
            var paymentService = applicationContext.getBean(PaymentService.class);
            var paymentRequest = PaymentRequest.builder()
                    .money(LocalMoney.of(1_000))
                    .build();
            var payment = paymentService.process(paymentRequest);
            log.info(payment.toString());
        }
    }
}
