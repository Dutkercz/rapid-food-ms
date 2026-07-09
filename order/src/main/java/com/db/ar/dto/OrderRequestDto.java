package com.db.ar.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record OrderRequestDto(
        @NotNull(message = "O ID de usuário é obrigatório") UUID userId,

        @NotNull(message = "O ID do vendedor é obrigatório") UUID vendorId,

        String observation,

        @NotNull(message = "Informe os itens do pedido") @Valid List<OrderItemRequestDto> items) {
}
