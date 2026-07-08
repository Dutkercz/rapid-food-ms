package com.db.ar.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductUpdateDto(
    @NotNull
    UUID id,

    String description,

    BigDecimal price) {
}
