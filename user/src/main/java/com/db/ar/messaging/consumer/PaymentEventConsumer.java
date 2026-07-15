package com.db.ar.messaging.consumer;

import com.db.ar.messaging.representation.payment.PaymentEventRep;
import com.db.ar.service.UserOrderService;
import com.db.ar.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class PaymentEventConsumer {

    private final ObjectMapper objectMapper;
    private final UserOrderService userOrderService;

    @KafkaListener(topics = "payment.created", groupId = "user.payments-created")
    public void paymentCreateEvent(String json) {
        try {
            log.info("Payment created event - PaymentEventRep {}", json);
            var representation = objectMapper.readValue(json, PaymentEventRep.class);
            userOrderService.createdPayment(representation);
        } catch (JsonProcessingException e) {
            log.error("Erro ao ler o json em created payment{}", json);
            throw new RuntimeException(e);
        }
    }

    @KafkaListener(topics = "payment.update", groupId = "user.payments-update")
    public void paymentUpdateEvent(String json) {
        try {
            log.info("Payment update event - PaymentEventRep {}", json);
            var representation = objectMapper.readValue(json, PaymentEventRep.class);
            userOrderService.updatePayment(representation);
        } catch (JsonProcessingException e) {
            log.error("Erro ao ler o json em update payment{}", json);
            throw new RuntimeException(e);
        }
    }
}
