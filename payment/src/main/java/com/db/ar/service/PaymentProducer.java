package com.db.ar.service;

import com.db.ar.dto.PaymentEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentProducer {

    private static final String PAYMENT_TOPIC = "payment-topic";

    private final KafkaTemplate<String, PaymentEvent> kafkaTemplate;

    public void sendPaymentEvent(PaymentEvent event) {
        kafkaTemplate.send(PAYMENT_TOPIC, event.paymentId().toString(), event);
        log.info("Payment event sent. paymentId={}, orderId={}",
                event.paymentId(),
                event.orderId());
    }
}
