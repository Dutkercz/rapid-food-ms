package com.db.ar.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductResponseDto(
        Long id,
        String productName,
        String description,
        BigDecimal price,
        Long vendorId,
        String vendorName,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) implements Serializable {
}
