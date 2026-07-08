package com.db.ar.dto;

import br.com.db.rapid_food_api.order.domain.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record OrderResponseDto(UUID id, BigDecimal totalAmount, String userName, String vendorName, OrderStatus status,
                               LocalDateTime createdAt, List<OrderItemResponseDto> items

) {
}
