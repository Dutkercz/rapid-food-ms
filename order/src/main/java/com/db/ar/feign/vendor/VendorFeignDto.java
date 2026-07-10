package com.db.ar.feign.vendor;

public record VendorFeignDto(
        Long id,
        String name,
        String cnpj,
        Boolean active
) {
}
