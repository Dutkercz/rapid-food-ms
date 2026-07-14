package com.db.ar.mapper;

import com.db.ar.domain.PaymentOrder;
import com.db.ar.messaging.representation.OrderEventRepresentation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentOrderMapper {


    @Mapping(target = "orderId", source = "rep.id")
    PaymentOrder toEntity(OrderEventRepresentation rep);
}
