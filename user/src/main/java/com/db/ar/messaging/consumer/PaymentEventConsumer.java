package com.db.ar.messaging.consumer;

import com.db.ar.messaging.representation.PaymentUpdateEventRep;
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

    @KafkaListener(topics = "payment.created", groupId = "user.payments")
    public void paymentUpdateEvent(String json) {
        try {
            var representation = objectMapper.readValue(json, PaymentUpdateEventRep.class);
            //resto da logica
        } catch (JsonProcessingException e) {
            log.error("Erro ao ler o json {}", json);
            throw new RuntimeException(e);
        }


    }
}
