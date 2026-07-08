package com.db.ar.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record OrderItemRequestDto(@NotNull(message = "O ID do produto é obrigatório") UUID productId,

                                  @NotNull(message = "A Quantidade é obrigatória") Integer quantity) {
}
