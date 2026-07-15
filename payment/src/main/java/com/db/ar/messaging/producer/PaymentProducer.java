package com.db.ar.messaging.producer;

import com.db.ar.messaging.representation.PaymentEventRep;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentProducer {

    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void orderSendToBank(PaymentEventRep representation) {
        try {
            log.info("Sending Payment to Bank {}", representation);
            var json = objectMapper.writeValueAsString(representation);
            kafkaTemplate.send("payment.created", representation.orderId().toString(), json);

        } catch (JsonProcessingException e) {
            log.error("Error at serializing Payment");
            throw new RuntimeException(e);
        }
    }


    public void paymentStatusUpdate(PaymentEventRep representation) {
        try {
            log.info("Sending Payment update {}", representation);
            var json = objectMapper.writeValueAsString(representation);
            kafkaTemplate.send("payment.update", representation.orderId().toString(), json);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
