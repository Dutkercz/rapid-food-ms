package com.db.ar.dto;

import com.db.ar.domain.enums.OrderPaymentStatus;
import com.db.ar.domain.enums.OrderStatus;

public record OrderStatusDto(
        Long id,
        OrderStatus orderStatus,
        OrderPaymentStatus paymentStatus,
        String observation) {
}
