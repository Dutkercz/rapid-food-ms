package com.db.ar.dto;

import com.db.ar.domain.enums.OrderPaymentStatus;
import com.db.ar.domain.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponseDto(
        Long id,
        BigDecimal totalAmount,
        String userName,
        String vendorName,
        OrderStatus orderStatus,
        OrderPaymentStatus paymentStatus,
        LocalDateTime createdAt,
        List<OrderItemResponseDto> items

) {
}
