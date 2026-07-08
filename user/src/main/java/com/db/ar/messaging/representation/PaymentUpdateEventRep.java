package com.db.ar.messaging.representation;

public record PaymentUpdateEventRep(
        Long userId,
        Long orderId,
        StatusPaymentRep statusPayment
) {
}
enum StatusPaymentRep {
    PAID,
    FAILED,
    CANCELLED,
}
