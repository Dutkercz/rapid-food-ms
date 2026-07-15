package com.db.ar.messaging.consumer.payment;

import com.db.ar.domain.enums.PaymentStatus;

import java.time.LocalDateTime;

public record PaymentEventRep(
        Long paymentId,
        Long orderId,
        String paymentKey,
        PaymentStatus paymentStatus,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
