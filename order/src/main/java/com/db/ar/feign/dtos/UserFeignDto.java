package com.db.ar.feign.dtos;

import java.time.LocalDateTime;

public record UserFeignDto(
        Long id,
        String name,
        String email,
        Boolean active,
        LocalDateTime createdAt) {
}
