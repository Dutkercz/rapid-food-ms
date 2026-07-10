package com.db.ar.dto;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record ProductUpdateDto(
        @NotNull
        Long id,
        String description,
        BigDecimal price) {
}
