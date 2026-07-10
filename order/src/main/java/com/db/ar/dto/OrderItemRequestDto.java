package com.db.ar.dto;

import jakarta.validation.constraints.NotNull;

public record OrderItemRequestDto(@NotNull(message = "O ID do produto é obrigatório") Long productId,

                                  @NotNull(message = "A Quantidade é obrigatória") Integer quantity) {
}
