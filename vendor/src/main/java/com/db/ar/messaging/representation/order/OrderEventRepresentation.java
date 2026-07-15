package com.db.ar.messaging.representation.order;

import com.db.ar.messaging.representation.commons.OrderStatusRep;
import com.db.ar.messaging.representation.commons.PaymentStatusRep;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderEventRepresentation(
        Long id,
        BigDecimal totalAmount,
        Long userId,
        Long vendorId,
        OrderStatusRep orderStatus,
        PaymentStatusRep paymentStatus,
        LocalDateTime createdAt
) {
}
