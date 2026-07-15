package com.db.ar.messaging.consumer.payment;

import com.db.ar.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentConsumer {

    private final ObjectMapper objectMapper;
    private final OrderService orderService;

    @KafkaListener(topics = "payment.created", groupId = "order.payment-created")
    public void consumerPaymentCreated(String json) {
        try {
            log.info("Consume PaymentCreated event: {}", json);
            var paymentRep = objectMapper.readValue(json, PaymentEventRep.class);
            orderService.createdPaymentOrder(paymentRep);
        } catch (JsonProcessingException e) {
            log.error("Erro at process event: {}", json, e);
            throw new RuntimeException(e);
        }
    }


    @KafkaListener(topics = "payment.update", groupId = "order.payment-update")
    public void consumerPaymentUpdate(String json) {
        try {
            log.info("Consume Payment update event: {}", json);
            var paymentRep = objectMapper.readValue(json, PaymentEventRep.class);
            orderService.updatePaymentOrder(paymentRep);
        } catch (JsonProcessingException e) {
            log.error("Erro at process event: {}", json, e);
            throw new RuntimeException(e);
        }
    }
}
