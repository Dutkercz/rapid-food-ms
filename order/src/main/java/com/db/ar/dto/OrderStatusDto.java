package com.db.ar.dto;

import br.com.db.rapid_food_api.order.domain.enums.OrderStatus;

import java.util.UUID;

public record OrderStatusDto(UUID id, OrderStatus status, String observation) {
}
