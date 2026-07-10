package com.db.ar.domain.enums;

public enum OrderPaymentStatus {
    PENDING,
    APPROVED,
    REJECTED,
    CANCELLED,
    REFUNDED;

    public boolean verifyToPrepareOrder(){
        return this == APPROVED;
    }
}
