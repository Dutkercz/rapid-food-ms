package com.db.ar.messaging.representation;

public enum PaymentStatusRep {
    PAID,
    FAILED,
    CANCELLED,
    PENDING,
    REFUNDED,
    WAITING_BANK_CALLBACK;

    public boolean cantBePaid() {
        return this == CANCELLED || this == PAID || this == REFUNDED;
    }

    public boolean cantBeFailed() {
        return this == REFUNDED || this == PAID || this == CANCELLED;
    }

    public boolean cantBeCancelled() {
        return this == REFUNDED || this == PAID || this == CANCELLED;
    }

    public boolean cantBeRefunded() {
        return this != PAID;
    }
}
