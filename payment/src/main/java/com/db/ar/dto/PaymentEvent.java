package com.db.ar.dto;

import com.db.ar.domain.enums.PaymentMethod;
import java.math.BigDecimal;

public record PaymentEvent(
        Long paymentId,
        Long orderId,
        BigDecimal amount,
        PaymentMethod paymentMethod) {
}
