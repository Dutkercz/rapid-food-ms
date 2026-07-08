package com.db.ar.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record ProductResponseDto(
    UUID id,
    String vendorName,
    String productName,
    BigDecimal price,
    String description,
    LocalDateTime createdAt, LocalDateTime updatedAt) {
}
