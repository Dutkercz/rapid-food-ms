package com.db.ar.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrderRequestDto(
        @NotNull(message = "O ID de usuário é obrigatório") Long userId,

        @NotNull(message = "O ID do vendedor é obrigatório") Long vendorId,

        String observation,

        @NotNull(message = "Informe os itens do pedido") @Valid List<OrderItemRequestDto> items) {
}
