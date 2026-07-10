package com.db.ar.service;

import com.db.ar.domain.Order;
import com.db.ar.domain.OrderItem;
import com.db.ar.domain.enums.OrderStatus;
import com.db.ar.dto.OrderCancelReasonDto;
import com.db.ar.dto.OrderRequestDto;
import com.db.ar.dto.OrderResponseDto;
import com.db.ar.dto.OrderStatusDto;
import com.db.ar.feign.ProductFeignClient;
import com.db.ar.feign.UserFeignClient;
import com.db.ar.feign.VendorFeignClient;
import com.db.ar.feign.dtos.ProductFeignDto;
import com.db.ar.feign.dtos.UserFeignDto;
import com.db.ar.feign.dtos.VendorFeignDto;
import com.db.ar.mapper.OrderItemMapper;
import com.db.ar.mapper.OrderMapper;
import com.db.ar.repository.OrderRepository;
import com.db.ar.service.utils.TotalAmountCalc;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final UserFeignClient userFeign;
    private final VendorFeignClient vendorFeign;
    private final ProductFeignClient productFeign;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;


    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto requestDto) {
        var vendor = findVendor(requestDto.vendorId());
        var user = findUser(requestDto.userId());

        var list = getOrderItems(requestDto).toList();
        var total = TotalAmountCalc.calculate(list);

        Order order = orderMapper.toEntity(requestDto, list, vendor, user, total);
        list.forEach(order::addItem);
        orderRepository.save(order);


        return orderMapper.toDtoResponse(order);
    }


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
        UserFeignDto userFeignDto = findUser(userId);
        Page<Order> orders = orderRepository.findAllByUserOrderedDesc(userFeignDto.id(), pageable);
        return orders.map(orderMapper::toDtoResponse);
    }

    public UserFeignDto getClient( Long clientId) {
        return findUser(clientId);
    }

    public VendorFeignDto getVendor( Long vendorId) {
        return findVendor(vendorId);
    }

    public ProductFeignDto getProduct( Long productId) {
        return findProduct(productId);
    }

    /// ===================================
    /// ========= Private Methods =========
    /// ===================================

    private Order findOrder( Long id) {
        return orderRepository.findById(id)
                              .orElseThrow(() -> new EntityNotFoundException("Order with id " + id + " not found"));
    }

    private Stream<OrderItem> getOrderItems(OrderRequestDto requestDto) {
        return requestDto.items().stream().map(orderItemReq -> {
            var product = findProduct(orderItemReq.productId());
            if (!product.vendorId().equals(requestDto.vendorId())) {
                throw new IllegalArgumentException("Invalid vendor id for item " + product.id());
            }
            return orderItemMapper.toEntity(orderItemReq, product);
        });
    }

    private UserFeignDto findUser( Long id) {
        ResponseEntity<UserFeignDto> response = userFeign.getById(id);
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id " + id + " not found");
        }
    }

    private VendorFeignDto findVendor( Long vendorId) {
        var response = vendorFeign.findById(vendorId);
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vendor with id " + vendorId + " not found");
        }
    }

    private ProductFeignDto findProduct( Long productId) {
        ResponseEntity<ProductFeignDto> response = productFeign.getById(productId);
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with id " + productId + " not found");
        }
    }
}
