package com.db.ar.messaging.representation;

import com.db.ar.domain.enums.PaymentMethod;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderEventRepresentation(
        Long id,
        BigDecimal totalAmount,
        Long userId,
        Long vendorId,
        OrderStatusRep orderStatus,
        String paymentKey,
        PaymentMethod paymentMethod,
        PaymentStatusRep paymentStatus,
        LocalDateTime createdAt
) {
}
