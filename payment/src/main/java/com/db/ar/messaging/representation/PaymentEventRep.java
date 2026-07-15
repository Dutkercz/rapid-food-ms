package com.db.ar.messaging.representation;

import java.time.LocalDateTime;

public record PaymentEventRep(
        Long paymentId,
        Long orderId,
        String paymentKey,
        PaymentStatusRep paymentStatus,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
