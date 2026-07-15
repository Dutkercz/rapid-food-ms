package com.db.ar.messaging.consumer.payment;

public enum PaymentStatusRep {
    PAID,
    FAILED,
    CANCELLED,
    PENDING,
    REFUNDED,
    WAITING_BANK_CALLBACK;
}
