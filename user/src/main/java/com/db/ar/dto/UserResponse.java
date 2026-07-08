package com.db.ar.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponse(UUID id, String name, String email, Boolean active, LocalDateTime createdAt) {
}
