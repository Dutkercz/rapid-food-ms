package com.db.ar.feign.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserFeignDto(
        UUID id,
        String name,
        String email,
        Boolean active,
        LocalDateTime createdAt) {
}
