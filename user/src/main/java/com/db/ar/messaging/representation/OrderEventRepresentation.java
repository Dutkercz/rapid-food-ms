package com.db.ar.messaging.representation;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderEventRepresentation(
        Long id,
        BigDecimal totalAmount,
        String vendorName,
        OrderStatusRep orderStatus,
        PaymentStatusRep paymentStatus,
        LocalDateTime createdAt
) {
}
