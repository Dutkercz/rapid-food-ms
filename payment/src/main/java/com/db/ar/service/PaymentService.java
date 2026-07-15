package com.db.ar.service;

import com.db.ar.domain.Payment;
import com.db.ar.dto.CallbackPaymentDto;
import com.db.ar.exception.InvalidPaymentStatusException;
import com.db.ar.exception.PaymentNotFoundException;
import com.db.ar.mapper.PaymentOrderMapper;
import com.db.ar.messaging.producer.PaymentProducer;
import com.db.ar.messaging.consumer.order.OrderEventRepresentation;
import com.db.ar.messaging.representation.PaymentMethod;
import com.db.ar.messaging.representation.PaymentStatusRep;
import com.db.ar.repository.PaymentRepository;
import com.db.ar.service.utils.ProcessPaymentStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final PaymentOrderMapper paymentOrderMapper;
    private final PaymentRepository paymentRepository;
    private final PaymentProducer paymentProducer;
    private final List<ProcessPaymentStatus> processPayment;


    @Transactional
    public void newPayment(OrderEventRepresentation representation) {
        Payment payment = paymentOrderMapper.toEntity(representation);

        //gera uma chave unica de pagamento
        payment.setPaymentKey(generatePaymentKey(payment.getOrderId(), payment.getPaymentMethod()));

        // "envia para o banco"
        sendPaymentToBank(payment);

        //efetua a persistencia no db para ter um id valido
        paymentRepository.save(payment);

        //publica o evento criação do pagamento com o status de espera de resposta do banco
        paymentProducer.orderSendToBank(paymentOrderMapper.toRepresentation(payment));

        log.info("Payment order has been saved successfully");
    }

    /// Metodo ficticios apenas mudam os status e geram chave de pagamento
    private String generatePaymentKey(Long id, PaymentMethod paymentMethod) {
        return  paymentMethod.name() + UUID.randomUUID() + id;
    }

    private void sendPaymentToBank(Payment payment) {
        payment.setPaymentStatus(PaymentStatusRep.WAITING_BANK_CALLBACK);
    }

    @Transactional
    public void bankCallback(@Valid CallbackPaymentDto request) {
        var payment = paymentRepository.findByIdAndPaymentKey(request.paymentId(), request.paymentKey())
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found for this paymentKey"));

        processPayment.stream().filter(valid ->
                                 valid.isStatusValid(request.paymentStatus()))
                      .findFirst()
                      .ifPresentOrElse(process ->
                                 process.processPaymentStatus(payment),
                      () -> {throw new InvalidPaymentStatusException("Payment status not found");});

        payment.setUpdatedAt(LocalDateTime.now());
        var representation = paymentOrderMapper.toRepresentation(payment);
        log.info("Payment sent to topic update.payment {}", representation);
        paymentProducer.paymentStatusUpdate(representation);
    }
}
