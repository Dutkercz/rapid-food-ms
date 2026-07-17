package com.db.ar.service;

import com.db.ar.domain.VendorOrder;
import com.db.ar.exception.VendorNotFoundException;
import com.db.ar.exception.VendorOrderNotFoundException;
import com.db.ar.mapper.VendorOrderMapper;
import com.db.ar.messaging.representation.order.OrderEventRepresentation;
import com.db.ar.messaging.representation.payment.PaymentEventRep;
import com.db.ar.repository.VendorOrderRepository;
import com.db.ar.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class VendorOrderService {

    private final VendorOrderRepository vendorOrderRepository;
    private final VendorOrderMapper vendorOrderMapper;
    private final VendorRepository vendorRepository;

    @Transactional
    public void newVendorOrder(OrderEventRepresentation representation) {
        findById(representation.vendorId());
        var vendorOrder = vendorOrderMapper.toEntity(representation);
        vendorOrderRepository.save(vendorOrder);
        log.info("Novo pedido salvo com sucesso. {}" , vendorOrder);
    }

    @Transactional
    public void createdPayment(PaymentEventRep representation) {
        var order = findOrder(representation.orderId());
        order.setPaymentStatus(representation.paymentStatus());
        order.setUpdatedAt(representation.updatedAt());
    }

    @Transactional
    public void updatePayment(PaymentEventRep representation) {
        var order = findOrder(representation.orderId());
        order.setPaymentStatus(representation.paymentStatus());
        order.setUpdatedAt(representation.updatedAt());
    }


    private VendorOrder findOrder(Long orderId){
        return vendorOrderRepository.findById(orderId).orElseThrow(() ->
                new VendorOrderNotFoundException("Order not found for this id"));
    }

    private void findById(Long vendorId) {
        vendorRepository.findById(vendorId).orElseThrow(() ->
                            new VendorNotFoundException("Restaurante não encontrado com o ID: " + vendorId));
    }
}
