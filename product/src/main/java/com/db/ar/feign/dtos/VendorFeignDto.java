package com.db.ar.feign.dtos;

import java.util.UUID;

public record VendorFeignDto(
        UUID id,
        String name,
        String cnpj,
        Boolean active
) {
}
