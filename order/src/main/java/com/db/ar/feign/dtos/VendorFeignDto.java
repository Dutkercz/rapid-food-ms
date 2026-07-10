package com.db.ar.feign.dtos;

public record VendorFeignDto(
        Long id,
        String name,
        String cnpj,
        Boolean active
) {
}
