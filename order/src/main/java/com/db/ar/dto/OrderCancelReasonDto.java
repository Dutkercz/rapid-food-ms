package com.db.ar.dto;

import jakarta.validation.constraints.NotBlank;

public record OrderCancelReasonDto(@NotBlank String reason) {
}
