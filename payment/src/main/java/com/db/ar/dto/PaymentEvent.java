package com.db.ar.dto;

import com.db.ar.domain.enums.PaymentMethod;
import java.math.BigDecimal;
import java.util.UUID;

public record PaymentEvent(
        UUID paymentId,
        UUID orderId,
        BigDecimal amount,
        PaymentMethod paymentMethod) {
}
