package com.db.ar.messaging.representation;

public record PaymentUpdateEventRep(
        Long userId,
        Long orderId,
        PaymentStatusRep statusPayment) {

}