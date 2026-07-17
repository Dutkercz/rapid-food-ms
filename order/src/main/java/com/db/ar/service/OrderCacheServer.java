package com.db.ar.service;

import com.db.ar.domain.Order;
import com.db.ar.exception.OrderNotFoundException;
import com.db.ar.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderCacheServer {


    private final OrderRepository orderRepository;

    @Cacheable(value = "order", key = "#id")
    public Order findOrder(Long id) {
        return orderRepository.findById(id)
                              .orElseThrow(() -> new OrderNotFoundException("Order with id " + id + " not found"));
    }

}
