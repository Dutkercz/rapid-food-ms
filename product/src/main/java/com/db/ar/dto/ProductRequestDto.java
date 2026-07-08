package com.db.ar.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductRequestDto(
    @NotNull(message = "O ID do vendedor é obrigatório")
    UUID vendorId,

    @NotNull(message = "O Nome do produto é obrigatório")
    String productName,

    @NotNull(message = "A Descrição é obrigatória")
    String description,

    @DecimalMin(value = "0.01", message = "O valor do produto excede o limite mínimo")
    @DecimalMax(value = "99999999.99", message = "O valor do produto excede o limite máximo")
    BigDecimal price) {
}
