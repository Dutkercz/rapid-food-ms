package com.db.ar.mapper;

import com.db.ar.domain.Order;
import com.db.ar.domain.OrderItem;
import com.db.ar.dto.OrderRequestDto;
import com.db.ar.dto.OrderResponseDto;
import com.db.ar.dto.OrderStatusDto;
import com.db.ar.feign.user.UserFeignDto;
import com.db.ar.feign.vendor.VendorFeignDto;
import com.db.ar.messaging.producer.representation.OrderProducerRep;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderResponseDto toDtoResponse(Order order);

    OrderStatusDto toOrderStatus(Order order);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "totalAmount", source = "total")
    @Mapping(target = "items", ignore = true)
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "userName", source = "user.name")
    @Mapping(target = "vendorId", source = "vendor.id")
    @Mapping(target = "vendorName", source = "vendor.name")
    Order toEntity(OrderRequestDto requestDto, List<OrderItem> items, VendorFeignDto vendor, UserFeignDto user, BigDecimal total);


    OrderProducerRep toOrderProducer(Order order);
}
