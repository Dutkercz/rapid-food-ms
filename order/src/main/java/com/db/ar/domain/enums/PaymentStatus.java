package com.db.ar.domain.enums;

public enum PaymentStatus {
    PAID,
    FAILED,
    CANCELLED,
    PENDING,
    REFUNDED,
    WAITING_BANK_CALLBACK;

    public boolean verifyToPrepareOrder(){
        return this == PAID;
    }
}
