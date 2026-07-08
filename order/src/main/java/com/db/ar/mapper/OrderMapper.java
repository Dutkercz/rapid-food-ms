package com.db.ar.mapper;

import br.com.db.rapid_food_api.order.domain.Order;
import br.com.db.rapid_food_api.order.domain.OrderItem;
import br.com.db.rapid_food_api.order.dto.OrderItemRequestDto;
import br.com.db.rapid_food_api.order.dto.OrderResponseDto;
import br.com.db.rapid_food_api.order.dto.OrderStatusDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "userName", source = "user.name")
    @Mapping(target = "vendorName", source = "vendor.name")
    OrderResponseDto toDtoResponse(Order order);

    List<OrderItem> toEntityOrderItems(List<OrderItemRequestDto> items);

    OrderStatusDto toOrderStatus(Order order);
}
