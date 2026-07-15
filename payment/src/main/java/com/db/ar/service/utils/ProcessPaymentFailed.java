package com.db.ar.service.utils;

import com.db.ar.domain.Payment;
import com.db.ar.exception.InvalidPaymentStatusException;
import com.db.ar.messaging.representation.PaymentStatusRep;
import org.springframework.stereotype.Component;

@Component
public class ProcessPaymentFailed implements ProcessPaymentStatus {
    @Override
    public void processPaymentStatus(Payment payment) {
        if (payment.getPaymentStatus().cantBeFailed()){
            throw new InvalidPaymentStatusException("This payment cant be mark as FAILED in this current status");
        }
        payment.setPaymentStatus(PaymentStatusRep.FAILED);
    }


    @Override
    public boolean isStatusValid(PaymentStatusRep statusRep) {
        return statusRep.equals(PaymentStatusRep.FAILED);
    }
}
