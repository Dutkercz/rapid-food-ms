package com.db.ar.controller;

import br.com.db.rapid_food_api.order.dto.OrderCancelReasonDto;
import br.com.db.rapid_food_api.order.dto.OrderRequestDto;
import br.com.db.rapid_food_api.order.dto.OrderResponseDto;
import br.com.db.rapid_food_api.order.dto.OrderStatusDto;
import br.com.db.rapid_food_api.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody @Valid OrderRequestDto orderRequestDto,
                                                        UriComponentsBuilder builder) {
        OrderResponseDto responseDto = orderService.createOrder(orderRequestDto);
        URI uri = builder.path("/orders/{id}").buildAndExpand(responseDto.id()).toUri();
        return ResponseEntity.created(uri).body(responseDto);
    }

    @GetMapping("/status/{id}")
    public ResponseEntity<OrderStatusDto> viewOrderStatus(@PathVariable UUID id) {
        return ResponseEntity.ok(orderService.viewOrderStatus(id));
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<OrderStatusDto> cancelOrder(@PathVariable UUID id,
                                                      @RequestBody @Valid OrderCancelReasonDto reasonDto) {
        return ResponseEntity.ok(orderService.cancelOrder(id, reasonDto));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Page<OrderResponseDto>> getAllOrders(@PathVariable UUID userId, Pageable pageable) {
        return ResponseEntity.ok(orderService.getOrders(userId, pageable));
    }
}
