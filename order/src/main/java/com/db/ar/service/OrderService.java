package com.db.ar.service;

import com.db.ar.domain.Order;
import com.db.ar.domain.OrderItem;
import com.db.ar.domain.enums.PaymentStatus;
import com.db.ar.dto.OrderCancelReasonDto;
import com.db.ar.dto.OrderRequestDto;
import com.db.ar.dto.OrderResponseDto;
import com.db.ar.dto.OrderStatusDto;
import com.db.ar.feign.product.ProductFeignDto;
import com.db.ar.feign.user.UserFeignDto;
import com.db.ar.feign.vendor.VendorFeignDto;
import com.db.ar.mapper.OrderItemMapper;
import com.db.ar.mapper.OrderMapper;
import com.db.ar.messaging.consumer.payment.PaymentEventRep;
import com.db.ar.messaging.producer.OrderProducer;
import com.db.ar.repository.OrderRepository;
import com.db.ar.service.utils.TotalAmountCalc;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final OrderProducer orderProducer;

    private final UserIntegrationService userIntegrationService;
    private final VendorIntegrationService vendorIntegrationService;
    private final ProductIntegrationService productIntegrationService;


    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto requestDto) {
        var vendor = getVendor(requestDto.vendorId());
        var user = getUser(requestDto.userId());

        var list = getOrderItems(requestDto);
        var total = TotalAmountCalc.calculate(list);

        Order order = orderMapper.toEntity(requestDto, list, vendor, user, total);
        list.forEach(order::addItem);
        order.setPaymentStatus(PaymentStatus.PENDING);
        orderRepository.save(order);

        orderProducer.sendOrder(orderMapper.toOrderProducer(order));

        return orderMapper.toDtoResponse(order);
    }
///==============================================
    @Transactional
    public void createdPaymentOrder(PaymentEventRep paymentRep) {
        Order order = findOrder(paymentRep.orderId());
        orderMapper.updateOrderFromPayment(paymentRep, order);
        order.setUpdatedAt(LocalDateTime.now());
    }
///  - createdPaymentOrder
///  - updatePaymentOrder
/// - METODOS ATUALMENTE SEGUEM A MESMO LOGICA, ASSIM COMO O TOPICO... POREM NO FUTURO PODEM E DEVEM TER UM LOGICA
/// DIFERENTE, POIS UM CRIA O PAGAMENTO E INSE INFOS, O OUTRO APENAS AS ATUALIZA.
    @Transactional
    public void updatePaymentOrder(PaymentEventRep paymentRep) {
        Order order = findOrder(paymentRep.orderId());
        orderMapper.updateOrderFromPayment(paymentRep, order);
        order.setUpdatedAt(LocalDateTime.now());
    }
///===================================================

    public OrderStatusDto viewOrderStatus( Long id) {
        Order order = findOrder(id);
        return orderMapper.toOrderStatus(order);
    }

    @Transactional
    public OrderStatusDto cancelOrder( Long id, @Valid OrderCancelReasonDto reasonDto) {
        Order order = findOrder(id);
        order.cancel(reasonDto.reason());

        // publicar evento para devolver pagamento

        order.setObservation(reasonDto.reason());
        return orderMapper.toOrderStatus(order);
    }

    public Page<OrderResponseDto> getOrders( Long userId, Pageable pageable) {
        UserFeignDto userFeignDto = getUser(userId);
        Page<Order> orders = orderRepository.findAllByUserOrderedDesc(userFeignDto.id(), pageable);
        return orders.map(orderMapper::toDtoResponse);
    }


    public UserFeignDto getUser(Long userId) {
        return userIntegrationService.findeUserById(userId);
    }

    public VendorFeignDto getVendor( Long vendorId) {
        return vendorIntegrationService.findVendorById(vendorId);
    }

    public ProductFeignDto getProduct(Long productId) {
        return productIntegrationService.findProductById(productId);
    }

    /// ===================================
    /// ========= Private Methods =========
    /// ===================================

    private Order findOrder( Long id) {
        return orderRepository.findById(id)
                              .orElseThrow(() -> new EntityNotFoundException("Order with id " + id + " not found"));
    }

    private List<OrderItem> getOrderItems(OrderRequestDto requestDto) {
        return requestDto.items().stream().map(orderItemReq -> {
            var product = getProduct(orderItemReq.productId());
            if (!Objects.equals(product.id(), requestDto.vendorId())) {
                throw new IllegalArgumentException("Invalid vendor id for item " + product.id());
            }
            return orderItemMapper.toEntity(orderItemReq, product);
        }).toList();
    }
}
