package com.db.ar.mapper;

import com.db.ar.domain.Product;
import com.db.ar.dto.ProductRequestDto;
import com.db.ar.dto.ProductResponseDto;
import com.db.ar.dto.ProductUpdateDto;
import com.db.ar.feign.dtos.VendorFeignDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "vendorId", source = "vendor.id")
    @Mapping(target = "vendorName", source = "vendor.name")
    Product toEntity(ProductRequestDto productRequestDto, VendorFeignDto vendor);

    ProductResponseDto toDto(Product product);

    void updateProduct(@MappingTarget Product product, ProductUpdateDto updateDto);
}
