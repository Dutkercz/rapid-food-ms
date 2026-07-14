package com.db.ar.messaging.producer.representation;

import com.db.ar.domain.enums.OrderPaymentStatus;
import com.db.ar.domain.enums.OrderStatus;
import com.db.ar.domain.enums.PaymentMethod;
import com.db.ar.dto.OrderItemResponseDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderProducerRep(
        Long id,
        BigDecimal totalAmount,
        Long userId,
        Long vendorId,
        OrderStatus orderStatus,
        OrderPaymentStatus paymentStatus,
        String paymentKey,
        PaymentMethod paymentMethod,
        LocalDateTime createdAt,
        List<OrderItemProducerRep> items
) {
}
