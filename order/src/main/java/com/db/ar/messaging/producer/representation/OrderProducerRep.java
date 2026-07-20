package com.db.ar.messaging.producer.representation;

import com.db.ar.domain.enums.OrderStatus;
import com.db.ar.domain.enums.PaymentMethod;
import com.db.ar.domain.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderProducerRep(
        Long id,
        BigDecimal totalAmount,
        Long userId,
        Long vendorId,
        OrderStatus orderStatus,
        PaymentStatus paymentStatus,
        String paymentKey,
        PaymentMethod paymentMethod,
        LocalDateTime createdAt,
        List<OrderItemProducerRep> items
) {
}
