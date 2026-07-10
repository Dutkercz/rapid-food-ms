package com.db.ar.mapper;

import com.db.ar.domain.OrderItem;
import com.db.ar.dto.OrderItemRequestDto;
import com.db.ar.dto.OrderItemResponseDto;
import com.db.ar.feign.dtos.ProductFeignDto;
import org.mapstruct.*;

import java.math.BigDecimal;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderItemMapper {

    @Mapping(target = "quantity", source = "orderItemReq.quantity")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "productId", source = "orderItemReq.productId")
    @Mapping(target = "productName", source = "productFeignDto.productName")
    @Mapping(target = "productPrice", source = "productFeignDto.price")
    OrderItem toEntity(OrderItemRequestDto orderItemReq, ProductFeignDto productFeignDto);

    @AfterMapping
    default void afterMapping(@MappingTarget OrderItem orderItem,
                              ProductFeignDto productFeignDto,
                              OrderItemRequestDto orderItemReq) {
        orderItem.setTotal(productFeignDto.price().multiply(
                BigDecimal.valueOf(orderItemReq.quantity())));
    }


    OrderItemResponseDto toResponseDto(OrderItem orderItem);
}
