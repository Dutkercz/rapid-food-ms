package com.db.ar.dto;

import java.math.BigDecimal;

public record OrderItemResponseDto(
        Long id,
        String productName,
        Integer quantity,
        BigDecimal productPrice,
        BigDecimal total) {
}
