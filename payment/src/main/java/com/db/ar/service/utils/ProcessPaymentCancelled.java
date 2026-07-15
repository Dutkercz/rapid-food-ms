package com.db.ar.service.utils;

import com.db.ar.domain.Payment;
import com.db.ar.exception.InvalidPaymentStatusException;
import com.db.ar.messaging.representation.PaymentStatusRep;
import org.springframework.stereotype.Component;

@Component
public class ProcessPaymentCancelled implements ProcessPaymentStatus{
    @Override
    public void processPaymentStatus(Payment payment) {
        if (payment.getPaymentStatus().cantBeCancelled()){
            throw new InvalidPaymentStatusException("This payment cant be mark as CANCELLED in this current status");
        }
        payment.setPaymentStatus(PaymentStatusRep.CANCELLED);
    }


    @Override
    public boolean isStatusValid(PaymentStatusRep statusRep) {
        return statusRep.equals(PaymentStatusRep.CANCELLED);
    }
}
