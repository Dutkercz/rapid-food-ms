package com.db.ar.dto;

import com.db.ar.messaging.representation.PaymentStatusRep;

public record CallbackPaymentDto(
       Long paymentId,
       String paymentKey,
       PaymentStatusRep paymentStatus
) {
}
