package com.db.ar.dto;

import com.db.ar.domain.enums.PaymentMethod;
import jakarta.validation.constraints.NotNull;

public record CreatePaymentRequest(
        @NotNull(message = "O ID do pedido é obrigatório") Long orderId,
        @NotNull(message = "O método de pagamento é obrigatório") PaymentMethod paymentMethod) {
}
