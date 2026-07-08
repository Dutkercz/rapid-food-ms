package com.db.ar.mapper;

import com.db.ar.domain.Payment;
import com.db.ar.dto.PaymentEvent;
import com.db.ar.dto.PaymentResponse;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    public PaymentResponse toResponse(Payment payment) {
        return new PaymentResponse(
                payment.getId(),
                null,//payment.getOrder().getId(),
                payment.getAmount(),
                payment.getStatus(),
                payment.getPaymentMethod(),
                payment.getCreatedAt(),
                payment.getUpdatedAt());
    }

    public PaymentEvent toEvent(Payment payment) {
        return new PaymentEvent(
                payment.getId(),
                null, //payment.getOrder().getId(),
                payment.getAmount(),
                payment.getPaymentMethod());
    }
}
