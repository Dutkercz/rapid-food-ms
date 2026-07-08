package com.db.ar.service;

import br.com.db.rapid_food_api.order.domain.Order;
import br.com.db.rapid_food_api.order.domain.OrderItem;
import br.com.db.rapid_food_api.order.domain.enums.OrderStatus;
import br.com.db.rapid_food_api.order.dto.*;
import br.com.db.rapid_food_api.order.mapper.OrderMapper;
import br.com.db.rapid_food_api.order.repository.OrderRepository;
import br.com.db.rapid_food_api.order.service.utils.ProductValidator;
import br.com.db.rapid_food_api.order.service.utils.TotalAmountCalc;
import br.com.db.rapid_food_api.product.domain.Product;
import br.com.db.rapid_food_api.user.domain.User;
import br.com.db.rapid_food_api.user.repository.UserRepository;
import br.com.db.rapid_food_api.vendors.domain.Vendor;
import br.com.db.rapid_food_api.vendors.repository.VendorRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ProductValidator productValidator;
    private final UserRepository userRepository;
    private final VendorRepository vendorRepository;

    private Order findOrder(UUID id) {
        return orderRepository.findById(id)
              .orElseThrow(() -> new EntityNotFoundException("Order with id " + id + " not found"));
    }

    private User findUser(UUID id) {
        return userRepository.findById(id)
             .orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found"));
    }

    private Vendor findVendor(UUID id) {
        return vendorRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Vendor with id " + id + " not found"));
    }

    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto) {
        Order order = new Order();
        order.setUser(findUser(orderRequestDto.userId()));

        for (OrderItemRequestDto itemDto : orderRequestDto.items()) {
            Product product = productValidator.validate(itemDto.productId());
            if (!product.getVendor().getId().equals(orderRequestDto.vendorId())) {
                throw new IllegalArgumentException("Invalid vendor id for item " + product.getId());
            }
            OrderItem orderItem = new OrderItem(product, itemDto.quantity());
            order.addItem(orderItem);
        }
        order.setVendor(findVendor(orderRequestDto.vendorId()));
        order.setTotalAmount(TotalAmountCalc.calculate(order));

        orderRepository.save(order);
        return orderMapper.toDtoResponse(order);
    }

    public OrderStatusDto viewOrderStatus(UUID id) {
        Order order = findOrder(id);
        return orderMapper.toOrderStatus(order);
    }

    @Transactional
    public OrderStatusDto cancelOrder(UUID id, @Valid OrderCancelReasonDto reasonDto) {
        Order order = findOrder(id);
        if (order.getStatus().cantBeCancelled()) {
            throw new IllegalStateException("Order cant be cancelled with status " + order.getStatus());
        }
        order.setStatus(OrderStatus.CANCELED);
        order.setObservation(reasonDto.reason());
        return orderMapper.toOrderStatus(order);
    }

    public Page<OrderResponseDto> getOrders(UUID userId, Pageable pageable) {
        Page<Order> orders = orderRepository.findAllByUserOrderedDesc(userId, pageable);
        return orders.map(orderMapper::toDtoResponse);
    }
}
