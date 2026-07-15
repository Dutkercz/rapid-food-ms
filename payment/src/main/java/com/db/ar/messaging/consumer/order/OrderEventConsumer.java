package com.db.ar.messaging.consumer.order;

import com.db.ar.service.PaymentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderEventConsumer {

    private final ObjectMapper objectMapper;
    private final PaymentService paymentService;

    @KafkaListener(topics = "${kafka.topics.order-create}", groupId = "payment.order-create")
    public void orderCreateEvent(String json){
        try {
            log.info("Evento de criação de pedido recebido: {}", json);
            var representation = objectMapper.readValue(json, OrderEventRepresentation.class);
            paymentService.newPayment(representation);
        } catch (JsonProcessingException e) {
            log.error("Erro ao receber order event {}", json, e);
            throw new RuntimeException(e);
        }
    }
}
