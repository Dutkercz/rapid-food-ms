package com.db.ar.mapper;

import com.db.ar.domain.Payment;
import com.db.ar.messaging.consumer.order.OrderEventRepresentation;
import com.db.ar.messaging.representation.PaymentEventRep;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentOrderMapper {


    @Mapping(target = "orderId", source = "rep.id")
    @Mapping(target = "id", ignore = true)
    Payment toEntity(OrderEventRepresentation rep);

    @Mapping(target = "paymentId", source = "id")
    PaymentEventRep toRepresentation(Payment payment);
}
