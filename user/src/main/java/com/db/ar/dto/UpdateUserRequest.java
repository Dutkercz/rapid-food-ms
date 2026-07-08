package com.db.ar.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UpdateUserRequest(@Size(max = 100, message = "O nome deve ter no máximo 100 caracteres") String name,
                                @Email(message = "E-mail inválido") @Size(max = 150, message = "O e-mail deve ter no " +
                                                                                               "máximo 150 " +
                                                                                               "caracteres") String email,
                                @Size(min = 6, max = 100, message = "A senha deve ter entre 6 e 100 caracteres") String password,
                                Boolean active) {
}
