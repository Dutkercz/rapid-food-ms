package com.db.ar.mapper;

import com.db.ar.domain.VendorOrder;
import com.db.ar.messaging.representation.order.OrderEventRepresentation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VendorOrderMapper {

    @Mapping(target = "orderId", source = "rep.id")
    VendorOrder toEntity(OrderEventRepresentation rep);
}
