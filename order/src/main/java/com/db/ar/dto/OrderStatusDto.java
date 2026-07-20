package com.db.ar.dto;

import com.db.ar.domain.enums.OrderStatus;
import com.db.ar.domain.enums.PaymentStatus;

public record OrderStatusDto(
        Long id,
        OrderStatus orderStatus,
        PaymentStatus paymentStatus,
        String observation) {
}
