package com.db.ar.mapper;

import com.db.ar.domain.UserOrder;
import com.db.ar.messaging.representation.OrderEventRepresentation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserOrderMapper {


    @Mapping(target = "orderId", source = "representation.id")
    UserOrder toEntity(OrderEventRepresentation representation);
}
