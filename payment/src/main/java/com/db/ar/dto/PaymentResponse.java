package com.db.ar.dto;

import com.db.ar.messaging.representation.PaymentMethod;
import com.db.ar.messaging.representation.PaymentStatusRep;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentResponse(
        Long id,
        Long orderId,
        BigDecimal amount,
        PaymentStatusRep paymentStatus,
        PaymentMethod paymentMethod,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {
}
