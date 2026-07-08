package com.db.ar.dto;

import java.util.UUID;

public record VendorResponse(
        UUID id,
        String name,
        String cnpj,
        Boolean active
) {}