package com.db.ar.feign.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductFeignDto(
        Long id,
        String productName,
        String description,
        BigDecimal price,
        Long vendorId,
        String vendorName,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
