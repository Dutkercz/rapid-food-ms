package com.db.ar.service.utils;

import com.db.ar.domain.Payment;
import com.db.ar.messaging.representation.PaymentStatusRep;
import org.springframework.stereotype.Component;

@Component
public interface ProcessPaymentStatus {

    void processPaymentStatus(Payment payment);

    boolean isStatusValid(PaymentStatusRep statusRep);
}
