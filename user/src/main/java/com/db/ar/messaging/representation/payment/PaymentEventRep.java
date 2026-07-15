package com.db.ar.messaging.representation.payment;

import com.db.ar.messaging.representation.commons.PaymentStatusRep;

import java.time.LocalDateTime;

public record PaymentEventRep(
        Long paymentId,
        Long orderId,
        String paymentKey,
        PaymentStatusRep paymentStatus,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {

}