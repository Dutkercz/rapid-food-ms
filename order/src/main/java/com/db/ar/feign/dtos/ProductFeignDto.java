package com.db.ar.feign.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record ProductFeignDto(
        UUID id,
        String productName,
        String description,
        BigDecimal price,
        UUID vendorId,
        String vendorName,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
