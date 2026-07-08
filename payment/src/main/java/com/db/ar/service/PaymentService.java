package com.db.ar.service;

import com.db.ar.domain.Payment;
import com.db.ar.mapper.PaymentMapper;
import com.db.ar.repository.PaymentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
   // private final OrderRepository orderRepository;
    private final PaymentMapper paymentMapper;
    private final PaymentProducer paymentProducer;

//    @Transactional
//    public PaymentResponse createPayment(CreatePaymentRequest request) {
       // Order order = orderRepository.findById(request.orderId())
              //  .orElseThrow(() -> new EntityNotFoundException("Order with id " + request.orderId() + " not found"));
//        if (order.getStatus().cantBePaid()){
//            throw new IllegalStateException("Order cant be paid with status" + order.getStatus());
//        }
//        Payment payment = new Payment(
//                null, //order,
//                request.paymentMethod());
//        paymentRepository.save(payment);
//        paymentProducer.sendPaymentEvent(
//                paymentMapper.toEvent(payment));
//        return paymentMapper.toResponse(payment);
//    }

    @Transactional
    public void confirmPayment(UUID paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new EntityNotFoundException("Payment with id " + paymentId + " not found"));
        payment.pay();
//        Order order = payment.getOrder();
//        order.markAsDelivered();
//        paymentRepository.save(payment);
//        orderRepository.save(order);
    }

}
