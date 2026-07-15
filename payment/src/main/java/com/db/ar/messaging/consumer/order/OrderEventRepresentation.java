package com.db.ar.messaging.consumer.order;

import com.db.ar.messaging.representation.OrderStatusRep;
import com.db.ar.messaging.representation.PaymentMethod;
import com.db.ar.messaging.representation.PaymentStatusRep;

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
