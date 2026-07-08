package com.db.ar.messaging.representation;

public record OrderEventRepresentation(
        Long userId,
        Long orderId,
        StatusPaymentRep statusPayment
) {
}
