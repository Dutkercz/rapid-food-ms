package com.db.ar.messaging.producer.representation;


import java.math.BigDecimal;

public record OrderItemProducerRep(
        Long id,
        Long productId,
        Integer quantity,
        BigDecimal total
) {
}
