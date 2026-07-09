package com.db.ar.dto;

import com.db.ar.domain.enums.OrderStatus;
import java.util.UUID;

public record OrderStatusDto(UUID id, OrderStatus status, String observation) {
}
