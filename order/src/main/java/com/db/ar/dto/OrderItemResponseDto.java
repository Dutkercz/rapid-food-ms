package com.db.ar.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderItemResponseDto(UUID id, Integer quantity, BigDecimal price, BigDecimal total, String productName) {
}
