package com.db.ar.domain.enums;

public enum OrderStatus {
    CREATED, SENT_TO_VENDOR, ACCEPTED, PREPARING, DELIVERED, CANCELED;

    public boolean cantBeCancelled() {
        return this == PREPARING || this == DELIVERED || this == CANCELED;
    }

    public boolean cantBePaid() {
        return this != CREATED;
    }

}
