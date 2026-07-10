package com.db.ar.controller;

import com.db.ar.dto.OrderCancelReasonDto;
import com.db.ar.dto.OrderRequestDto;
import com.db.ar.dto.OrderResponseDto;
import com.db.ar.dto.OrderStatusDto;
import com.db.ar.feign.product.ProductFeignDto;
import com.db.ar.feign.user.UserFeignDto;
import com.db.ar.feign.vendor.VendorFeignDto;
import com.db.ar.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;

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
    public ResponseEntity<OrderStatusDto> viewOrderStatus(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.viewOrderStatus(id));
    }


    //================= supplier init
    @GetMapping("/vendor/{vendorId}")
    public ResponseEntity<VendorFeignDto> viewOrderVendor(@PathVariable Long vendorId) {
        return ResponseEntity.ok(orderService.getVendor(vendorId));
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<UserFeignDto> viewOrderClient(@PathVariable Long clientId) {
        return ResponseEntity.ok(orderService.getClient(clientId));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ProductFeignDto> viewOrderProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(orderService.getProduct(productId));
    }
    //========= end


    @PutMapping("/cancel/{id}")
    public ResponseEntity<OrderStatusDto> cancelOrder(@PathVariable Long id,
                                                      @RequestBody @Valid OrderCancelReasonDto reasonDto) {
        return ResponseEntity.ok(orderService.cancelOrder(id, reasonDto));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Page<OrderResponseDto>> getAllOrders(@PathVariable Long userId, Pageable pageable) {
        return ResponseEntity.ok(orderService.getOrders(userId, pageable));
    }
}
