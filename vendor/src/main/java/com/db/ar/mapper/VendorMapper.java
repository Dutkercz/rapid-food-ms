package com.db.ar.mapper;


import com.db.ar.domain.Vendor;
import com.db.ar.dto.CreateVendorRequest;
import com.db.ar.dto.VendorResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VendorMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", constant = "true")
    Vendor toEntity(CreateVendorRequest request);

    VendorResponse toResponse(Vendor vendor);
}