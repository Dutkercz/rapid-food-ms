package com.db.ar.dto;

import com.db.ar.domain.enums.PaymentStatus;
import com.db.ar.domain.enums.OrderStatus;

public record OrderStatusDto(
        Long id,
        OrderStatus orderStatus,
        PaymentStatus paymentStatus,
        String observation) {
}
