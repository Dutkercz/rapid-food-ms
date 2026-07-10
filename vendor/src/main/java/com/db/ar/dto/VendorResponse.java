package com.db.ar.dto;

public record VendorResponse(
        Long id,
        String name,
        String cnpj,
        Boolean active
) {}