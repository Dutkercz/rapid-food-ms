package com.db.ar.messaging.consumer;

import com.db.ar.messaging.representation.OrderEventRepresentation;
import com.db.ar.service.VendorService;
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
    private final VendorService vendorService;


    @KafkaListener(topics = "order.created", groupId = "vendor.order-create")
    public void orderCreatedEvent(String json) {
        try {
            log.info("Recebendo evento de criação de pedido: {}", json);
            var representation = objectMapper.readValue(json, OrderEventRepresentation.class);
            vendorService.newVendorOrder(representation);
        } catch (JsonProcessingException e) {
            log.error("Erro ao receber evento de pedido: {}", json, e);
            throw new RuntimeException(e);
        }
    }
}
