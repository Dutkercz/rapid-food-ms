package com.db.ar.service.utils;

import com.db.ar.domain.Payment;
import com.db.ar.exception.InvalidPaymentStatusException;
import com.db.ar.messaging.representation.PaymentStatusRep;
import org.springframework.stereotype.Component;

@Component
public class ProcessPaymentPaid implements ProcessPaymentStatus {
    @Override
    public void processPaymentStatus(Payment payment) {
        if (payment.getPaymentStatus().cantBePaid()) {
            throw new InvalidPaymentStatusException("This payment cant be mark as PAID in this current status");
        }
        payment.setPaymentStatus(PaymentStatusRep.PAID);
    }


    @Override
    public boolean isStatusValid(PaymentStatusRep statusRep) {
        return statusRep.equals(PaymentStatusRep.PAID);
    }
}
