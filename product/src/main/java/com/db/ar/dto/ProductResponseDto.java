package com.db.ar.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record ProductResponseDto(
        UUID id,
        String productName,
        String description,
        BigDecimal price,
        UUID vendorId,
        String vendorName,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {
}
