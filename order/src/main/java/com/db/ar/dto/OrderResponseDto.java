package com.db.ar.dto;

import com.db.ar.domain.enums.PaymentStatus;
import com.db.ar.domain.enums.OrderStatus;
import com.db.ar.domain.enums.PaymentMethod;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponseDto(
        Long id,
        BigDecimal totalAmount,
        String userName,
        String vendorName,
        String observation,
        PaymentMethod paymentMethod,
        OrderStatus orderStatus,
        PaymentStatus paymentStatus,
        LocalDateTime createdAt,
        List<OrderItemResponseDto> items

) {
}
