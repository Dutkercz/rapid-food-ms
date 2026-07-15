package com.db.ar.service;

import com.db.ar.domain.UserOrder;
import com.db.ar.mapper.UserOrderMapper;
import com.db.ar.messaging.representation.order.OrderEventRepresentation;
import com.db.ar.messaging.representation.payment.PaymentEventRep;
import com.db.ar.repository.UserOrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserOrderService {

    private final UserOrderRepository userOrderRepository;
    private final UserOrderMapper userOrderMapper;

    @Transactional
    public void saveUserOrder(OrderEventRepresentation representation) {
        var userOrder = userOrderMapper.toEntity(representation);
        userOrderRepository.save(userOrder);
        log.info("UserOrder salvo com sucesso {}", userOrder);
    }

    @Transactional
    public void createdPayment(PaymentEventRep representation) {
        UserOrder userOrder = findOrder(representation.orderId());
        userOrder.setPaymentStatus(representation.paymentStatus());
        userOrder.setUpdatedAt(representation.updatedAt());
    }

    @Transactional
    public void updatePayment(PaymentEventRep representation) {
        var  userOrder = findOrder(representation.orderId());
        userOrder.setPaymentStatus(representation.paymentStatus());
        userOrder.setUpdatedAt(representation.updatedAt());
    }

    private UserOrder findOrder(Long orderId){
        return userOrderRepository.findById(orderId).orElseThrow(() ->
                           new EntityNotFoundException("Not found with this id " + orderId));
    }
}
