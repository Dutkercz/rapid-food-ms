package com.db.ar.service.utils;

import com.db.ar.domain.Payment;
import com.db.ar.exception.InvalidPaymentStatusException;
import com.db.ar.messaging.representation.PaymentStatusRep;
import org.springframework.stereotype.Component;

@Component
public class ProcessPaymentRefunded implements ProcessPaymentStatus {

    @Override
    public void processPaymentStatus(Payment payment) {
        if (payment.getPaymentStatus().cantBeRefunded()){
            throw new InvalidPaymentStatusException("This payment cant be refunded in this current status");
        }
        payment.setPaymentStatus(PaymentStatusRep.REFUNDED);
    }

    @Override
    public boolean isStatusValid(PaymentStatusRep statusRep) {
        return statusRep.equals(PaymentStatusRep.REFUNDED);
    }
}
