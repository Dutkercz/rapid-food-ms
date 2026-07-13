package com.db.ar.messaging.consumer;

import com.db.ar.messaging.representation.OrderEventRepresentation;
import com.db.ar.service.UserOrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderEventConsumer {

    private final ObjectMapper objectMapper;
    private final UserOrderService userOrderService;

    @KafkaListener(topics = "order.created", groupId = "user.orders")
    public void orderCreatedEvent(String json){
        try {
            log.info("Evento de criação de pedido recebido {}" , json);
            var respresentation = objectMapper.readValue(json, OrderEventRepresentation.class);
            userOrderService.saveUserOrder(respresentation);

        } catch (JsonProcessingException e) {
            log.error("Erro no evento de criação de pedido {}", json);
            throw new RuntimeException(e);
        }

    }
}
