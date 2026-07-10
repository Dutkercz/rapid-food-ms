package com.db.ar.messaging;

import com.db.ar.domain.Order;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Value("${kafka.topics.create-order}")
    private String topicCreateOrder;

    @Value("${kafka.topics.update-order}")
    private String topicUpdateOrder;

    @Value("${kafka.topics.delete-order}")
    private String topicDeleteOrder;

    public void sendOrder(Order order) {
        try {
            var json = objectMapper.writeValueAsString(order);
            kafkaTemplate.send(topicCreateOrder,order.getId().toString(), json);
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }

}
